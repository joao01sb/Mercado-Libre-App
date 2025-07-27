package com.joao01sb.mercadolibreapp.presentation.ui.state

data class SearchUiState(
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList()
)