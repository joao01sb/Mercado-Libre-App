package com.joao01sb.mercadolibreapp.data.remote

import android.content.Context
import android.util.Log
import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import com.joao01sb.mercadolibreapp.data.remote.service.ApiService
import com.joao01sb.mercadolibreapp.data.remote.utils.ApiExceptionMapper
import com.joao01sb.mercadolibreapp.data.remote.utils.AssetFileReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class ApiServiceImp(
    context: Context
) : ApiService {

    companion object {
        private const val TAG = "ApiServiceImp"
    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        encodeDefaults = true
    }

    private val fileReader = AssetFileReader(context)

    override fun searchProducts(query: String, offset: Int, limit: Int): Flow<SearchResponse> =
        flow {
            try {
                Log.d(TAG, "Searching products with query: '$query'")
                val fileName = fileReader.buildSearchFileName(query)
                val jsonString = fileReader.readFile(fileName)
                val response = json.decodeFromString<SearchResponse>(jsonString)

                emit(response)

            } catch (e: Exception) {
                Log.e(TAG, "Error searching products for query: '$query'", e)
                throw ApiExceptionMapper.mapToApiException(e, "Failed to search products")
            }
        }

    override fun getProductDetails(productId: String): Flow<ProductDetailResponse> =
        flow {
            try {
                Log.d(TAG, "Getting product details for ID: '$productId'")
                val fileName = "itens/item-$productId.json"
                val jsonString = fileReader.readFile(fileName)
                val response = json.decodeFromString<ProductDetailResponse>(jsonString)

                emit(response)

            } catch (e: Exception) {
                Log.e(TAG, "Error getting product details for ID: '$productId'", e)
                throw ApiExceptionMapper.mapToApiException(e, "Failed to get product details")
            }
        }

    override fun getProductDescription(productId: String): Flow<ProductDescriptionResponse> =
        flow {
            try {
                Log.d(TAG, "Getting product description for ID: '$productId'")
                val fileName = "descriptions/item-$productId-description.json"
                val jsonString = fileReader.readFile(fileName)
                val response = json.decodeFromString<ProductDescriptionResponse>(jsonString)

                emit(response)

            } catch (e: Exception) {
                Log.e(TAG, "Error getting product description for ID: '$productId'", e)
                throw ApiExceptionMapper.mapToApiException(e, "Failed to get product description")
            }
        }

    override fun getProductCategory(categoryId: String): Flow<CategoryResponse> =
        flow {
            try {
                Log.d(TAG, "Getting category for ID: '$categoryId'")
                val fileName = "categories/item-$categoryId-category.json"
                val jsonString = fileReader.readFile(fileName)
                val response = json.decodeFromString<CategoryResponse>(jsonString)

                emit(response)

            } catch (e: Exception) {
                Log.e(TAG, "Error getting category for ID: '$categoryId'", e)
                throw ApiExceptionMapper.mapToApiException(e, "Failed to get category")
            }
        }
}