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
    val freeShipping: Boolean = false,
    val rating: Double? = null,
    val installmentsQuantity: Int = 1,
    val installmentsRate: Double = 0.0,
    val sellerId: Long? = null,
    val categoryId: String? = null,
    val isSponsored: Boolean = false,
    val reviewsCount: Int = 0
)
