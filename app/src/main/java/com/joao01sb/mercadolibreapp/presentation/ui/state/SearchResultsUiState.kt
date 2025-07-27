package com.joao01sb.mercadolibreapp.presentation.ui.state

import com.joao01sb.mercadolibreapp.domain.model.Product

data class SearchResultsUiData(
    val query: String = "",
    val products: List<Product> = emptyList(),
    val hasSearched: Boolean = false
)