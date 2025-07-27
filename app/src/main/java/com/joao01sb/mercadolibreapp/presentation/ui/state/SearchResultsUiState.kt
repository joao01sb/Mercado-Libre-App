package com.joao01sb.mercadolibreapp.presentation.ui.state

import com.joao01sb.mercadolibreapp.domain.model.Product

data class SearchResultsUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val hasSearched: Boolean = false
)