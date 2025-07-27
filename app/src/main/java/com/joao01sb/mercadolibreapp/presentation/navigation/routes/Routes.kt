package com.joao01sb.mercadolibreapp.presentation.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object SearchRoute

@Serializable
data class SearchResultsRoute(
    val query: String
)

@Serializable
data class ProductDetailRoute(
    val productId: String,
    val query: String
)
