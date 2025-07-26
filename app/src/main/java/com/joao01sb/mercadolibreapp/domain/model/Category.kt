package com.joao01sb.mercadolibreapp.domain.model

data class Category(
    val id: String,
    val name: String,
    val picture: String? = null,
    val totalItems: Int = 0
)
