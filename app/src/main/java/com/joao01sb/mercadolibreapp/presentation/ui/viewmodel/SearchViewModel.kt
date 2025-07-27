package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.mercadolibreapp.domain.usecase.GetRecentSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.SaveSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    init {
        loadSearchHistory()
    }

    fun changedSearchProducts(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchQuery = if (query.isBlank()) "" else query
                )
            }
        }
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            getRecentSearchHistoryUseCase().collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        _uiState.update {
                            it.copy(
                                recentSearches = result.data
                            )
                        }
                    }
                    is ResultUseCase.Error -> {
                        _error.value = "Error loading search history: ${result.message}"
                    }
                }
            }
        }
    }

    fun saveSearchQuery() {
        viewModelScope.launch {
            val result = saveSearchHistoryUseCase(_uiState.value.searchQuery)
            when (result) {
                is ResultUseCase.Success -> {
                    loadSearchHistory()
                }
                is ResultUseCase.Error -> {
                    _error.value = "Error saving search query: ${result.message}"
                }
            }
        }
    }

}