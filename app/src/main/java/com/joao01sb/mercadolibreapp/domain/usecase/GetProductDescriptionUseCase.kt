package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.domain.util.safeExecute
import kotlinx.coroutines.flow.Flow

class GetProductDescriptionUseCase(
    private val productRepository: ProductRepository
) {
    companion object {
        private const val TAG = "GetProductDescriptionUseCase"
    }

    operator fun invoke(productId: String): Flow<ResultUseCase<ProductDescription>> {
        Log.d(TAG, "invoke: Getting product description for productId='$productId'")

        return safeExecute(
            tag = TAG,
            validation = {
                DomainValidator.validateProductId(productId)
            },
            execution = {
                productRepository.getProductDescription(productId)
            },
            errorMessage = "Failed to get product description"
        )
    }
}
