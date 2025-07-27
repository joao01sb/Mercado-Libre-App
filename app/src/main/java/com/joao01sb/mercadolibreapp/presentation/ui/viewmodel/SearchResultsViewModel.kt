package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.usecase.SearchProductsUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchResultsUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SearchResultsViewModel"
    }


    private val query: String = checkNotNull(saveStateHandle["query"]) { "Missing query argument" }

    private val _uiState = MutableStateFlow<UiState<SearchResultsUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<SearchResultsUiData>> = _uiState.asStateFlow()

    val currentQuery: String
        get() = query

    init {
        Log.d(TAG, "init: Initializing SearchResultsViewModel with query='$query'")
        searchProducts(query)
    }

    fun searchProducts(query: String) {
        Log.d(TAG, "searchProducts: Starting search for query='$query'")

        if (query.isBlank()) {
            Log.w(TAG, "searchProducts: Query is blank, emitting error")
            _uiState.value = UiState.Error("Search query cannot be empty")
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "searchProducts: Setting loading state")
            _uiState.value = UiState.Loading

            searchProductsUseCase(query).collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        Log.d(TAG, "searchProducts: Success - Found ${result.data.size} products")
                        _uiState.value = UiState.Success(
                            SearchResultsUiData(
                                query = query,
                                products = result.data,
                                hasSearched = true
                            )
                        )
                    }
                    is ResultUseCase.Error -> {
                        Log.e(TAG, "searchProducts: Error - ${result.message}")
                        _uiState.value = UiState.Error(result.message)
                    }
                }
            }
        }
    }

    fun retrySearch() {
        Log.d(TAG, "retrySearch: User requested retry for query='$query'")
        searchProducts(query)
    }
}
