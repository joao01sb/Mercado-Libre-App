package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.mercadolibreapp.domain.usecase.SearchProductsUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchResultsUiState
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

    private val query: String = checkNotNull(saveStateHandle["query"]) { "Missing query argument" }

    private val _uiState = MutableStateFlow(SearchResultsUiState())
    val uiState: StateFlow<SearchResultsUiState> = _uiState.asStateFlow()

    init {
        searchProducts(query)
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                query = query,
                hasSearched = true
            )

            searchProductsUseCase(query).collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        _uiState.value = _uiState.value.copy(
                            products = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is ResultUseCase.Error -> {
                        _uiState.value = _uiState.value.copy(
                            products = emptyList(),
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun retrySearch() {
        val currentQuery = _uiState.value.query
        if (currentQuery.isNotBlank()) {
            searchProducts(currentQuery)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
