package com.joao01sb.mercadolibreapp.data.remote.service

import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ApiService {

    fun searchProducts(
        query: String,
        offset: Int = 0,
        limit: Int = 50
    ): Flow<SearchResponse>

    fun getProductDetails(
        productId: String
    ): Flow<ProductDetailResponse>

    fun getProductDescription(
        productId: String
    ): Flow<ProductDescriptionResponse>

    fun getProductCategory(
        categoryId: String
    ): Flow<CategoryResponse>

}