package com.joao01sb.mercadolibreapp.domain.model

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val currencyId: String,
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int,
    val permalink: String,
    val originalPrice: Double? = null,
    val freeShipping: Boolean = false
)
