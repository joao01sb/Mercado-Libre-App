package com.joao01sb.mercadolibreapp.domain.repository

import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun searchProducts(
        query: String,
        offset: Int = 0,
        limit: Int = 50
    ): Flow<List<Product>>

    fun getProductDetails(
        productId: String
    ): Flow<ProductDetail>

    fun getProductDescription(
        productId: String
    ): Flow<ProductDescription>

    fun getProductCategory(
        categoryId: String
    ): Flow<Category>
}
