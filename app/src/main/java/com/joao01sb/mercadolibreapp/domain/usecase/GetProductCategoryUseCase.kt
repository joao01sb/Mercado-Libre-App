package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.domain.util.safeExecute
import kotlinx.coroutines.flow.Flow

class GetProductCategoryUseCase(
    private val productRepository: ProductRepository
) {
    companion object {
        private const val TAG = "GetProductCategoryUseCase"
    }

    operator fun invoke(categoryId: String): Flow<ResultUseCase<Category>> {
        Log.d(TAG, "invoke: Starting to get category for categoryId='$categoryId'")

        return safeExecute(
            tag = TAG,
            validation = {
                DomainValidator.validateCategoryId(categoryId)
            },
            execution = {
                productRepository.getProductCategory(categoryId)
            },
            errorMessage = "Failed to get category"
        )
    }
}
