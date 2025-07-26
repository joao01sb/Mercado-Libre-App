package com.joao01sb.mercadolibreapp.domain.util


object DomainValidator {

    private const val MAX_QUERY_LENGTH = 100

    fun validateSearchQuery(query: String) {
        when {
            query.isBlank() -> throw IllegalArgumentException("Search query cannot be empty")
            query.length > MAX_QUERY_LENGTH -> throw IllegalArgumentException("Search query is too long")
        }
    }

    fun validateProductId(productId: String) {
        when {
            productId.isBlank() -> throw IllegalArgumentException("Product ID cannot be empty")
            productId.length < 3 -> throw IllegalArgumentException("Product ID is too short")
        }
    }

    fun validateCategoryId(categoryId: String) {
        when {
            categoryId.isBlank() -> throw IllegalArgumentException("Category ID cannot be empty")
            categoryId.length < 3 -> throw IllegalArgumentException("Category ID is too short")
        }
    }

    fun validatePaginationParams(offset: Int, limit: Int) {
        when {
            offset < 0 -> throw IllegalArgumentException("Offset cannot be negative")
            limit <= 0 -> throw IllegalArgumentException("Limit must be greater than 0")
            limit > 200 -> throw IllegalArgumentException("Limit cannot exceed 200")
        }
    }
}
