package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductCategoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDescriptionUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDetailsUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.ProductDetailUiData
import com.joao01sb.mercadolibreapp.presentation.util.ProductSerializationUtil
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase,
    private val getProductCategoryUseCase: GetProductCategoryUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProductDetailViewModel"
    }


    private val productId: String = checkNotNull(savedStateHandle["productId"]) { "Missing productId argument" }
    private val query: String = savedStateHandle["query"] ?: ""
    private val productJson: String = savedStateHandle["productJson"] ?: ""


    init {
        Log.d(TAG, "init: Initializing ProductDetailViewModel with productId='$productId'")
        Log.d(TAG, "init: Initializing ProductDetailViewModel with query='$query'")
        Log.d(TAG, "init: Initializing ProductDetailViewModel with productJson='$productJson'")
    }

    val currentQuery: String
        get() = query

    private val _uiState = MutableStateFlow<UiState<ProductDetailUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<ProductDetailUiData>> = _uiState.asStateFlow()

    init {
        initializeProductData()
    }

    private fun initializeProductData() {
        Log.d(TAG, "initializeProductData: Starting initialization for productId=$productId")
        val baseProduct = ProductSerializationUtil.deserializeProduct(productJson)

        Log.d(TAG, "initializeProductData: BaseProduct deserialized=${baseProduct != null}")
        _uiState.value = UiState.Success(
            ProductDetailUiData(
                baseProduct = baseProduct
            )
        )

        loadProductDetails(productId)

        baseProduct?.id?.let { categoryId ->
            Log.d(TAG, "initializeProductData: Loading category for categoryId=$categoryId")
            loadProductCategory(categoryId)
        }
    }

    private fun loadProductDetails(productId: String) {
        Log.d(TAG, "loadProductDetails: Starting to load details for productId=$productId")
        viewModelScope.launch {
            getProductDetailsUseCase(productId).collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        Log.d(TAG, "loadProductDetails: Success - ProductDetail loaded")
                        updateUiData { currentData ->
                            currentData.copy(productDetail = result.data)
                        }
                        loadProductDescription(productId)
                    }
                    is ResultUseCase.Error -> {
                        Log.d(TAG, "loadProductDetails: Error - ${result.message}")
                        updateUiData { currentData ->
                            currentData.copy(productDetail = null)
                        }
                    }
                }
            }
        }
    }

    private fun loadProductDescription(productId: String) {
        Log.d(TAG, "loadProductDescription: Starting to load description for productId=$productId")
        viewModelScope.launch {
            getProductDescriptionUseCase(productId).collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        Log.d(TAG, "loadProductDescription: Success - Description loaded")
                        updateUiData { currentData ->
                            currentData.copy(description = result.data.plainText)
                        }
                    }
                    is ResultUseCase.Error -> {
                        Log.w(TAG, "loadProductDescription: Error - ${result.message}")
                        updateUiData { currentData ->
                            currentData.copy(description = null)
                        }
                    }
                }
            }
        }
    }

    private fun loadProductCategory(categoryId: String) {
        Log.d(TAG, "loadProductCategory: Starting to load category for categoryId=$categoryId")
        viewModelScope.launch {
            getProductCategoryUseCase(categoryId).collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        Log.d(TAG, "loadProductCategory: Success - Category loaded: ${result.data.name}")
                        updateUiData { currentData ->
                            currentData.copy(category = result.data)
                        }
                    }
                    is ResultUseCase.Error -> {
                        Log.w(TAG, "loadProductCategory: Error - ${result.message}")
                        updateUiData { currentData ->
                            currentData.copy(category = null)
                        }
                    }
                }
            }
        }
    }

    private fun updateUiData(update: (ProductDetailUiData) -> ProductDetailUiData) {
        when (val currentState = _uiState.value) {
            is UiState.Success -> {
                _uiState.value = UiState.Success(update(currentState.data))
            }
            else -> {
                _uiState.value = UiState.Success(update(ProductDetailUiData(query = "")))
            }
        }
    }

    fun retryLoadProduct() {
        Log.d(TAG, "retryLoadProduct: User requested retry")
        initializeProductData()
    }
}
