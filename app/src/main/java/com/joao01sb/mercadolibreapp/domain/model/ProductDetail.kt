package com.joao01sb.mercadolibreapp.domain.model

data class ProductDetail(
    val id: String,
    val title: String,
    val price: Double,
    val originalPrice: Double? = null,
    val currencyId: String,
    val condition: String,
    val thumbnail: String,
    val pictures: List<String>,
    val permalink: String,
    val categoryId: String,
    val sellerId: Long,
    val acceptsMercadoPago: Boolean,
    val freeShipping: Boolean = false,
    val availableQuantity: Int,
    val attributes: List<ProductAttribute>,
    val rating: Double? = null,
    val reviewsCount: Int = 0,
    val warranty: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val installmentsQuantity: Int = 1,
    val installmentsRate: Double = 0.0,
    val tags: List<String> = emptyList()
)
