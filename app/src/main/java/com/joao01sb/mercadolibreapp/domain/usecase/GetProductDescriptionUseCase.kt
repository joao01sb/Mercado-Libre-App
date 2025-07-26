package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductDescriptionUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<ResultUseCase<ProductDescription>> {
        return try {
            DomainValidator.validateProductId(productId)

            productRepository.getProductDescription(productId)
                .map { productDescription ->
                    ResultUseCase.Success(productDescription)
                }
                .catch { exception ->
                    ResultUseCase.Error("Failed to get product description: ${exception.message}")
                }
        } catch (e: IllegalArgumentException) {
            flowOf(ResultUseCase.Error(e.message ?: "Invalid product ID"))
        }
    }
}
