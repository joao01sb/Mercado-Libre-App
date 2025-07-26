package com.joao01sb.mercadolibreapp.data.repository

import com.joao01sb.mercadolibreapp.data.mapper.toDomain
import com.joao01sb.mercadolibreapp.domain.datasource.RemoteDataSource
import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl (
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {

    override fun searchProducts(query: String, offset: Int, limit: Int): Flow<List<Product>> {
        return remoteDataSource.searchProducts(query, offset, limit)
            .map { response: SearchResponse -> response.toDomain() }
    }

    override fun getProductDetails(productId: String): Flow<ProductDetail> {
        return remoteDataSource.getProductDetails(productId)
            .map { response: ProductDetailResponse -> response.toDomain() }
    }

    override fun getProductDescription(productId: String): Flow<ProductDescription> {
        return remoteDataSource.getProductDescription(productId)
            .map { response: ProductDescriptionResponse -> response.toDomain() }
    }

    override fun getProductCategory(categoryId: String): Flow<Category> {
        return remoteDataSource.getProductCategory(categoryId)
            .map { response: CategoryResponse -> response.toDomain() }
    }
}
