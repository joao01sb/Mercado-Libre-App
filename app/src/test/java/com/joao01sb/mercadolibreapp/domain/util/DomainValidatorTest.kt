package com.joao01sb.mercadolibreapp.domain.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DomainValidatorTest {

    @Test
    fun `validateSearchQuery should pass with valid query`() {
        val validQuery = "iphone 13"
        DomainValidator.validateSearchQuery(validQuery)
    }

    @Test
    fun `validateSearchQuery should pass with maximum length query`() {
        val maxLengthQuery = "a".repeat(100)
        DomainValidator.validateSearchQuery(maxLengthQuery)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateSearchQuery should throw exception when query is empty`() {
        DomainValidator.validateSearchQuery("")
    }

    @Test
    fun `validateSearchQuery should throw exception with correct message when query is empty`() {
        try {
            DomainValidator.validateSearchQuery("")
        } catch (e: IllegalArgumentException) {
            assertEquals("Search query cannot be empty", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateSearchQuery should throw exception when query is blank`() {
        DomainValidator.validateSearchQuery("   ")
    }

    @Test
    fun `validateSearchQuery should throw exception with correct message when query is blank`() {
        try {
            DomainValidator.validateSearchQuery("   ")
        } catch (e: IllegalArgumentException) {
            assertEquals("Search query cannot be empty", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateSearchQuery should throw exception when query exceeds maximum length`() {
        val tooLongQuery = "a".repeat(101)
        DomainValidator.validateSearchQuery(tooLongQuery)
    }

    @Test
    fun `validateSearchQuery should throw exception with correct message when query is too long`() {
        val tooLongQuery = "a".repeat(101)
        try {
            DomainValidator.validateSearchQuery(tooLongQuery)
        } catch (e: IllegalArgumentException) {
            assertEquals("Search query is too long", e.message)
        }
    }

    @Test
    fun `validateProductId should pass with valid product ID`() {
        val validProductId = "MLA123456789"
        DomainValidator.validateProductId(validProductId)
    }

    @Test
    fun `validateProductId should pass with minimum length product ID`() {
        val minLengthProductId = "ABC"
        DomainValidator.validateProductId(minLengthProductId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateProductId should throw exception when product ID is empty`() {
        DomainValidator.validateProductId("")
    }

    @Test
    fun `validateProductId should throw exception with correct message when product ID is empty`() {
        try {
            DomainValidator.validateProductId("")
        } catch (e: IllegalArgumentException) {
            assertEquals("Product ID cannot be empty", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateProductId should throw exception when product ID is blank`() {
        DomainValidator.validateProductId("   ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateProductId should throw exception when product ID is too short`() {
        DomainValidator.validateProductId("AB")
    }

    @Test
    fun `validateProductId should throw exception with correct message when product ID is too short`() {
        try {
            DomainValidator.validateProductId("AB")
        } catch (e: IllegalArgumentException) {
            assertEquals("Product ID is too short", e.message)
        }
    }

    @Test
    fun `validateCategoryId should pass with valid category ID`() {
        val validCategoryId = "MLA1055"
        DomainValidator.validateCategoryId(validCategoryId)
    }

    @Test
    fun `validateCategoryId should pass with minimum length category ID`() {
        val minLengthCategoryId = "CAT"
        DomainValidator.validateCategoryId(minLengthCategoryId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateCategoryId should throw exception when category ID is empty`() {
        DomainValidator.validateCategoryId("")
    }

    @Test
    fun `validateCategoryId should throw exception with correct message when category ID is empty`() {
        try {
            DomainValidator.validateCategoryId("")
        } catch (e: IllegalArgumentException) {
            assertEquals("Category ID cannot be empty", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateCategoryId should throw exception when category ID is blank`() {
        DomainValidator.validateCategoryId("  ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validateCategoryId should throw exception when category ID is too short`() {
        DomainValidator.validateCategoryId("CA")
    }

    @Test
    fun `validateCategoryId should throw exception with correct message when category ID is too short`() {
        try {
            DomainValidator.validateCategoryId("CA")
        } catch (e: IllegalArgumentException) {
            assertEquals("Category ID is too short", e.message)
        }
    }

    @Test
    fun `validatePaginationParams should pass with valid parameters`() {
        DomainValidator.validatePaginationParams(0, 50)
        DomainValidator.validatePaginationParams(10, 20)
        DomainValidator.validatePaginationParams(100, 200)
    }

    @Test
    fun `validatePaginationParams should pass with maximum limit`() {
        DomainValidator.validatePaginationParams(0, 200)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validatePaginationParams should throw exception when offset is negative`() {
        DomainValidator.validatePaginationParams(-1, 50)
    }

    @Test
    fun `validatePaginationParams should throw exception with correct message when offset is negative`() {
        try {
            DomainValidator.validatePaginationParams(-1, 50)
        } catch (e: IllegalArgumentException) {
            assertEquals("Offset cannot be negative", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validatePaginationParams should throw exception when limit is zero`() {
        DomainValidator.validatePaginationParams(0, 0)
    }

    @Test
    fun `validatePaginationParams should throw exception with correct message when limit is zero`() {
        try {
            DomainValidator.validatePaginationParams(0, 0)
        } catch (e: IllegalArgumentException) {
            assertEquals("Limit must be greater than 0", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validatePaginationParams should throw exception when limit is negative`() {
        DomainValidator.validatePaginationParams(0, -5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validatePaginationParams should throw exception when limit exceeds maximum`() {
        DomainValidator.validatePaginationParams(0, 201)
    }

    @Test
    fun `validatePaginationParams should throw exception with correct message when limit exceeds maximum`() {
        try {
            DomainValidator.validatePaginationParams(0, 201)
        } catch (e: IllegalArgumentException) {
            assertEquals("Limit cannot exceed 200", e.message)
        }
    }
}
