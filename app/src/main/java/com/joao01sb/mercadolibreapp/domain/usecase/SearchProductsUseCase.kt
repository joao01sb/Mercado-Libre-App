package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.domain.util.safeExecute
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase (
    private val productRepository: ProductRepository
) {
    companion object {
        private const val TAG = "SearchProductsUseCase"
    }

    operator fun invoke(
        query: String,
        offset: Int = 0,
        limit: Int = 50
    ): Flow<ResultUseCase<List<Product>>> {
        Log.d(TAG, "invoke: Searching products with query='$query', offset=$offset, limit=$limit")

        return safeExecute(
            tag = TAG,
            validation = {
                DomainValidator.validateSearchQuery(query)
                DomainValidator.validatePaginationParams(offset, limit)
            },
            execution = {
                productRepository.searchProducts(query, offset, limit)
            },
            errorMessage = "Failed to search products"
        )
    }
}
