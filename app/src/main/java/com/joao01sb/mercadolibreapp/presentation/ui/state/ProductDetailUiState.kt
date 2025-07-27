package com.joao01sb.mercadolibreapp.presentation.ui.state

import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail

data class ProductDetailUiData(
    val baseProduct: Product? = null,
    val productDetail: ProductDetail? = null,
    val description: String? = null,
    val category: Category? = null,
    val query: String = ""
)