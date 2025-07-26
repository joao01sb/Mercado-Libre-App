package com.joao01sb.mercadolibreapp.data.remote.datasource

import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import com.joao01sb.mercadolibreapp.data.remote.service.ApiService
import com.joao01sb.mercadolibreapp.domain.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {

    override fun searchProducts(query: String, offset: Int, limit: Int): Flow<SearchResponse> {
        return apiService.searchProducts(query, offset, limit)
    }

    override fun getProductDetails(productId: String): Flow<ProductDetailResponse> {
        return apiService.getProductDetails(productId)
    }

    override fun getProductDescription(productId: String): Flow<ProductDescriptionResponse> {
        return apiService.getProductDescription(productId)
    }

    override fun getProductCategory(categoryId: String): Flow<CategoryResponse> {
        return apiService.getProductCategory(categoryId)
    }
}
