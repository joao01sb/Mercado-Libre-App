package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.DomainValidator
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.domain.util.safeExecute
import kotlinx.coroutines.flow.Flow

class GetProductDetailsUseCase(
    private val productRepository: ProductRepository
) {
    companion object {
        private const val TAG = "GetProductDetailsUseCase"
    }

    operator fun invoke(productId: String): Flow<ResultUseCase<ProductDetail>> {
        Log.d(TAG, "invoke: Getting product details for productId='$productId'")

        return safeExecute(
            tag = TAG,
            validation = {
                DomainValidator.validateProductId(productId)
            },
            execution = {
                productRepository.getProductDetails(productId)
            },
            errorMessage = "Failed to get product details"
        )
    }
}
