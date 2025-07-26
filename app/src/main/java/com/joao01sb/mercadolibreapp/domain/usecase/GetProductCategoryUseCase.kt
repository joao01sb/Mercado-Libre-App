package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductCategoryUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(categoryId: String): Flow<ResultUseCase<Category>> {
        return try {
            DomainValidator.validateCategoryId(categoryId)

            productRepository.getProductCategory(categoryId)
                .map { category ->
                    ResultUseCase.Success(category)
                }
                .catch { exception ->
                    ResultUseCase.Error("Failed to get category: ${exception.message}")
                }
        } catch (e: IllegalArgumentException) {
            flowOf(ResultUseCase.Error(e.message ?: "Invalid category ID"))
        }
    }
}
