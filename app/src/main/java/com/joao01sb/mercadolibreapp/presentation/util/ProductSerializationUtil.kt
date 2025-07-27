package com.joao01sb.mercadolibreapp.presentation.util

import com.joao01sb.mercadolibreapp.domain.model.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ProductSerializationUtil {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

    fun serializeProduct(product: Product): String {
        return try {
            json.encodeToString(product)
        } catch (e: Exception) {
            createBasicProductJson(product)
        }
    }

    fun deserializeProduct(productJson: String): Product? {
        return try {
            json.decodeFromString<Product>(productJson)
        } catch (e: Exception) {
            null
        }
    }

    private fun createBasicProductJson(product: Product): String {
        return """
            {
                "id": "${product.id}",
                "title": "${product.title.replace("\"", "\\\"")}",
                "price": ${product.price},
                "currencyId": "${product.currencyId}",
                "thumbnail": "${product.thumbnail}",
                "condition": "${product.condition}",
                "availableQuantity": ${product.availableQuantity},
                "permalink": "${product.permalink}",
                "originalPrice": ${product.originalPrice},
                "freeShipping": ${product.freeShipping},
                "rating": ${product.rating},
                "installmentsQuantity": ${product.installmentsQuantity},
                "installmentsRate": ${product.installmentsRate},
                "sellerId": ${product.sellerId},
                "categoryId": "${product.categoryId ?: ""}",
                "isSponsored": ${product.isSponsored},
                "reviewsCount": ${product.reviewsCount}
            }
        """.trimIndent()
    }
}
