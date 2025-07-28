package com.joao01sb.mercadolibreapp.data.remote.datasource

import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.data.remote.service.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class RemoteDataSourceImplTest {

    private lateinit var apiService: ApiService
    private lateinit var dataSource: RemoteDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        dataSource = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `searchProducts should return SearchResponse on success`() = runTest {
        val query = "samsung"
        val offset = 0
        val limit = 10
        val mockResponse = MockProductData.mockCompleteSearchResponse

        coEvery { apiService.searchProducts(query, offset, limit) } returns flowOf(mockResponse)

        dataSource.searchProducts(query, offset, limit).test {
            val actual = awaitItem()
            assertEquals(mockResponse.query, actual.query)
            assertEquals(mockResponse.resultProducts.size, actual.resultProducts.size)
            assertEquals(mockResponse.siteId, actual.siteId)
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.searchProducts(query, offset, limit) }
    }

    @Test
    fun `searchProducts should propagate FileNotFoundException from ApiService`() = runTest {
        val query = "nonexistent"
        val offset = 0
        val limit = 10
        val originalError = FileNotFoundException("Mock file not found")

        coEvery { apiService.searchProducts(query, offset, limit) } returns flow {
            throw originalError
        }

        dataSource.searchProducts(query, offset, limit).test {
            val thrown = awaitError()
            assertTrue("Should be FileNotFoundException", thrown is FileNotFoundException)
            assertEquals("Mock file not found", thrown.message)
        }

        coVerify(exactly = 1) { apiService.searchProducts(query, offset, limit) }
    }

    @Test
    fun `getProductDetails should return ProductDetailResponse on success`() = runTest {
        val productId = "MLA2005705454"
        val mockResponse = MockProductData.mockProductDetailResponse

        coEvery { apiService.getProductDetails(productId) } returns flowOf(mockResponse)

        dataSource.getProductDetails(productId).test {
            val actual = awaitItem()
            assertEquals(mockResponse.id, actual.id)
            assertEquals(mockResponse.title, actual.title)
            assertEquals(mockResponse.price, actual.price, 0.01)
            assertEquals(mockResponse.categoryId, actual.categoryId)
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.getProductDetails(productId) }
    }

    @Test
    fun `getProductDetails should propagate SerializationException from ApiService`() = runTest {
        val productId = "MLA123456789"
        val originalError = SerializationException("Invalid JSON structure")

        coEvery { apiService.getProductDetails(productId) } returns flow {
            throw originalError
        }

        dataSource.getProductDetails(productId).test {
            val thrown = awaitError()
            assertTrue("Should be SerializationException", thrown is SerializationException)
            assertEquals("Invalid JSON structure", thrown.message)
        }

        coVerify(exactly = 1) { apiService.getProductDetails(productId) }
    }

    @Test
    fun `getProductDescription should return ProductDescriptionResponse on success`() = runTest {
        val productId = "MLA2005705454"
        val mockResponse = MockProductData.mockProductDescriptionResponse

        coEvery { apiService.getProductDescription(productId) } returns flowOf(mockResponse)

        dataSource.getProductDescription(productId).test {
            val actual = awaitItem()
            assertEquals(mockResponse.plainText, actual.plainText)
            assertEquals(mockResponse.text, actual.text)
            assertEquals(mockResponse.lastUpdated, actual.lastUpdated)
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.getProductDescription(productId) }
    }

    @Test
    fun `getProductDescription should propagate RuntimeException from ApiService`() = runTest {
        val productId = "MLA987654321"
        val originalError = RuntimeException("Unexpected parsing error")

        coEvery { apiService.getProductDescription(productId) } returns flow {
            throw originalError
        }

        dataSource.getProductDescription(productId).test {
            val thrown = awaitError()
            assertTrue("Should be RuntimeException", thrown is RuntimeException)
            assertEquals("Unexpected parsing error", thrown.message)
        }

        coVerify(exactly = 1) { apiService.getProductDescription(productId) }
    }

    @Test
    fun `getProductCategory should return CategoryResponse on success`() = runTest {
        val categoryId = "MLA1055"
        val mockResponse = MockProductData.mockCategoryResponse

        coEvery { apiService.getProductCategory(categoryId) } returns flowOf(mockResponse)

        dataSource.getProductCategory(categoryId).test {
            val actual = awaitItem()
            assertEquals(mockResponse.id, actual.id)
            assertEquals(mockResponse.name, actual.name)
            assertEquals(mockResponse.picture, actual.picture)
            assertEquals(mockResponse.totalItemsInThisCategory, actual.totalItemsInThisCategory)
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.getProductCategory(categoryId) }
    }

    @Test
    fun `getProductCategory should propagate IllegalArgumentException from ApiService`() = runTest {
        val categoryId = "INVALID_CATEGORY"
        val originalError = IllegalArgumentException("Invalid category format")

        coEvery { apiService.getProductCategory(categoryId) } returns flow {
            throw originalError
        }

        dataSource.getProductCategory(categoryId).test {
            val thrown = awaitError()
            assertTrue("Should be IllegalArgumentException", thrown is IllegalArgumentException)
            assertEquals("Invalid category format", thrown.message)
        }

        coVerify(exactly = 1) { apiService.getProductCategory(categoryId) }
    }
}
