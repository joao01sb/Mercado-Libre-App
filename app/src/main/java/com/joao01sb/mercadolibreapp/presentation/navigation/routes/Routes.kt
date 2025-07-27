package com.joao01sb.mercadolibreapp.presentation.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class SearchRoute(
    val initialQuery: String = ""
)

@Serializable
data class SearchResultsRoute(
    val query: String
)

@Serializable
data class ProductDetailRoute(
    val productId: String,
    val query: String,
    val productJson: String // JSON serializado do Product para passar via navegação
)
