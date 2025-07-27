package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory
import com.joao01sb.mercadolibreapp.domain.usecase.GetRecentSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.SaveSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
) : ViewModel() {

    private val initialQuery: String = savedStateHandle["initialQuery"] ?: ""

    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<SearchUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<SearchUiData>> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "init: Initializing SearchViewModel")
        Log.d(TAG, "init: Initial query is '$initialQuery'")
        loadSearchHistory()
    }

    fun changedSearchProducts(query: String) {
        Log.d(TAG, "changedSearchProducts: Query changed to '$query'")
        when (val currentState = _uiState.value) {
            is UiState.Success -> {
                Log.d(TAG, "changedSearchProducts: Updating existing success state")
                _uiState.update {
                    UiState.Success(
                        currentState.data.copy(searchQuery = query.ifBlank { "" })
                    )
                }
            }
            else -> {
                Log.d(TAG, "changedSearchProducts: Creating new success state")
                _uiState.update {
                    UiState.Success(SearchUiData(searchQuery = query.ifBlank { "" }))
                }
            }
        }
    }

    private fun loadSearchHistory() {
        Log.d(TAG, "loadSearchHistory: Starting to load recent searches")
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getRecentSearchHistoryUseCase().collect { result ->
                when (result) {
                    is ResultUseCase.Success -> {
                        Log.d(TAG, "loadSearchHistory: Success - Found ${result.data.size} recent searches")
                        val currentQuery = initialQuery

                        _uiState.update {
                            UiState.Success(
                                SearchUiData(
                                    searchQuery = currentQuery,
                                    recentSearches = result.data
                                )
                            )
                        }
                    }
                    is ResultUseCase.Error -> {
                        Log.e(TAG, "loadSearchHistory: Error - ${result.message}")
                        _uiState.update {
                            UiState.Error(result.message)
                        }
                    }
                }
            }
        }
    }

    fun saveSearch(query: String) {
        Log.d(TAG, "saveSearch: Saving search query '$query'")
        viewModelScope.launch {
            when(val result = saveSearchHistoryUseCase(query)) {
                is ResultUseCase.Success -> {
                    Log.d(TAG, "saveSearch: Successfully saved search query")
                }
                is ResultUseCase.Error -> {
                    Log.e(TAG, "saveSearch: Error - ${result.message}")
                    _uiState.update {
                        UiState.Error(result.message)
                    }
                }
            }
        }
    }

}