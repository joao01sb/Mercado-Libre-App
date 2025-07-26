package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchProductsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        query: String,
        offset: Int = 0,
        limit: Int = 50
    ): Flow<ResultUseCase<List<Product>>> {
        return try {
            DomainValidator.validateSearchQuery(query)
            DomainValidator.validatePaginationParams(offset, limit)

            productRepository.searchProducts(query, offset, limit)
                .map { products ->
                    ResultUseCase.Success(products)
                }
                .catch { exception ->
                    ResultUseCase.Error("Failed to search products: ${exception.message}")
                }
        } catch (e: IllegalArgumentException) {
            flowOf(ResultUseCase.Error(e.message ?: "Invalid parameters"))
        }
    }
}
