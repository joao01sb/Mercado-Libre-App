package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetProductDetailsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<ResultUseCase<ProductDetail>> {
        return try {
            DomainValidator.validateProductId(productId)

            productRepository.getProductDetails(productId)
                .map { productDetail ->
                    ResultUseCase.Success(productDetail)
                }
                .catch { exception ->
                    ResultUseCase.Error("Failed to get product details: ${exception.message}")
                }
        } catch (e: IllegalArgumentException) {
            flowOf(ResultUseCase.Error(e.message ?: "Invalid product ID"))
        }
    }
}
