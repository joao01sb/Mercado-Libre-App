package com.joao01sb.mercadolibreapp.data.repository

import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.data.mapper.toDomain
import com.joao01sb.mercadolibreapp.domain.datasource.RemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

class ProductRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = mockk(relaxed = true)
        repository = ProductRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `searchProducts should return mapped list on success`() = runTest {
        val query = "iphone"
        val offset = 0
        val limit = 50
        val mockResponse = MockProductData.mockCompleteSearchResponse
        val expectedProducts = mockResponse.toDomain()

        coEvery { remoteDataSource.searchProducts(query, offset, limit) } returns flowOf(mockResponse)

        repository.searchProducts(query, offset, limit).test {
            val actual = awaitItem()
            assertEquals(expectedProducts.size, actual.size)
            assertEquals(expectedProducts.first().id, actual.first().id)
            assertEquals(expectedProducts.first().title, actual.first().title)
            assertEquals(expectedProducts.first().price, actual.first().price, 0.01)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.searchProducts(query, offset, limit) }
    }

    @Test
    fun `searchProducts should handle empty query string`() = runTest {
        val query = ""
        val offset = 0
        val limit = 10
        val emptyResponse = MockProductData.mockCompleteSearchResponse.copy(resultProducts = emptyList())

        coEvery { remoteDataSource.searchProducts(query, offset, limit) } returns flowOf(emptyResponse)

        repository.searchProducts(query, offset, limit).test {
            val actual = awaitItem()
            assertTrue("Should return empty list", actual.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `searchProducts should handle network error`() = runTest {
        val query = "test"
        val offset = 0
        val limit = 10
        val networkError = UnknownHostException("No internet connection")

        coEvery { remoteDataSource.searchProducts(query, offset, limit) } returns flow { throw networkError }

        repository.searchProducts(query, offset, limit).test {
            val thrown = awaitError()
            assertTrue("Should be UnknownHostException", thrown is UnknownHostException)
            assertEquals("No internet connection", thrown.message)
        }

        coVerify(exactly = 1) { remoteDataSource.searchProducts(query, offset, limit) }
    }

    @Test
    fun `searchProducts should handle pagination parameters`() = runTest {
        val query = "samsung"
        val offset = 50
        val limit = 25
        val mockResponse = MockProductData.mockCompleteSearchResponse

        coEvery { remoteDataSource.searchProducts(query, offset, limit) } returns flowOf(mockResponse)

        repository.searchProducts(query, offset, limit).test {
            awaitItem()
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.searchProducts(query, offset, limit) }
    }

    @Test
    fun `getProductDetails should return mapped product detail on success`() = runTest {
        val productId = "MLA2005705454"
        val mockResponse = MockProductData.mockProductDetailResponse
        val expectedDetail = mockResponse.toDomain()

        coEvery { remoteDataSource.getProductDetails(productId) } returns flowOf(mockResponse)

        repository.getProductDetails(productId).test {
            val actual = awaitItem()
            assertEquals(expectedDetail.id, actual.id)
            assertEquals(expectedDetail.title, actual.title)
            assertEquals(expectedDetail.price, actual.price, 0.01)
            assertEquals(expectedDetail.pictures.size, actual.pictures.size)
            assertEquals(expectedDetail.attributes.size, actual.attributes.size)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.getProductDetails(productId) }
    }

    @Test
    fun `getProductDetails should handle invalid product ID format`() = runTest {
        val invalidProductId = "INVALID123"
        val error = IllegalArgumentException("Invalid product ID format")

        coEvery { remoteDataSource.getProductDetails(invalidProductId) } returns flow { throw error }

        repository.getProductDetails(invalidProductId).test {
            val thrown = awaitError()
            assertTrue("Should be IllegalArgumentException", thrown is IllegalArgumentException)
            assertEquals("Invalid product ID format", thrown.message)
        }
    }

    @Test
    fun `getProductDetails should handle product not found`() = runTest {
        val productId = "MLA999999999"
        val error = IOException("Product not found")

        coEvery { remoteDataSource.getProductDetails(productId) } returns flow { throw error }

        repository.getProductDetails(productId).test {
            val thrown = awaitError()
            assertTrue("Should be IOException", thrown is IOException)
            assertEquals("Product not found", thrown.message)
        }
    }

    @Test
    fun `getProductDescription should return mapped description on success`() = runTest {
        val productId = "MLA2005705454"
        val mockResponse = MockProductData.mockProductDescriptionResponse
        val expectedDescription = mockResponse.toDomain()

        coEvery { remoteDataSource.getProductDescription(productId) } returns flowOf(mockResponse)

        repository.getProductDescription(productId).test {
            val actual = awaitItem()
            assertEquals(expectedDescription.plainText, actual.plainText)
            assertEquals(expectedDescription.text, actual.text)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.getProductDescription(productId) }
    }

    @Test
    fun `getProductDescription should handle empty description`() = runTest {
        val productId = "MLA123456789"
        val emptyDescriptionResponse = MockProductData.mockProductDescriptionResponse.copy(
            plainText = "",
            text = ""
        )

        coEvery { remoteDataSource.getProductDescription(productId) } returns flowOf(emptyDescriptionResponse)

        repository.getProductDescription(productId).test {
            val actual = awaitItem()
            assertTrue("Description should be empty", actual.plainText.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `getProductDescription should handle service unavailable error`() = runTest {
        val productId = "MLA2005705454"
        val serviceError = IOException("Service temporarily unavailable")

        coEvery { remoteDataSource.getProductDescription(productId) } returns flow { throw serviceError }

        repository.getProductDescription(productId).test {
            val thrown = awaitError()
            assertTrue("Should be IOException", thrown is IOException)
            assertEquals("Service temporarily unavailable", thrown.message)
        }
    }

    @Test
    fun `getProductCategory should return mapped category on success`() = runTest {
        val categoryId = "MLA1055"
        val mockResponse = MockProductData.mockCategoryResponse
        val expectedCategory = mockResponse.toDomain()

        coEvery { remoteDataSource.getProductCategory(categoryId) } returns flowOf(mockResponse)

        repository.getProductCategory(categoryId).test {
            val actual = awaitItem()
            assertEquals(expectedCategory.id, actual.id)
            assertEquals(expectedCategory.name, actual.name)
            assertEquals(expectedCategory.picture, actual.picture)
            assertEquals(expectedCategory.totalItems, actual.totalItems)
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.getProductCategory(categoryId) }
    }

    @Test
    fun `getProductCategory should handle invalid category ID`() = runTest {
        val invalidCategoryId = "INVALID_CAT"
        val error = IllegalArgumentException("Invalid category ID")

        coEvery { remoteDataSource.getProductCategory(invalidCategoryId) } returns flow { throw error }

        repository.getProductCategory(invalidCategoryId).test {
            val thrown = awaitError()
            assertTrue("Should be IllegalArgumentException", thrown is IllegalArgumentException)
            assertEquals("Invalid category ID", thrown.message)
        }
    }

    @Test
    fun `getProductCategory should handle category not found`() = runTest {
        val categoryId = "MLA999999"
        val error = IOException("Category not found")

        coEvery { remoteDataSource.getProductCategory(categoryId) } returns flow { throw error }

        repository.getProductCategory(categoryId).test {
            val thrown = awaitError()
            assertTrue("Should be IOException", thrown is IOException)
            assertEquals("Category not found", thrown.message)
        }
    }

    @Test
    fun `repository should handle multiple concurrent calls`() = runTest {
        val productId = "MLA2005705454"
        val categoryId = "MLA1055"
        val query = "test"

        coEvery { remoteDataSource.getProductDetails(productId) } returns flowOf(MockProductData.mockProductDetailResponse)
        coEvery { remoteDataSource.getProductCategory(categoryId) } returns flowOf(MockProductData.mockCategoryResponse)
        coEvery { remoteDataSource.searchProducts(query, 0, 10) } returns flowOf(MockProductData.mockCompleteSearchResponse)

        repository.getProductDetails(productId).test {
            awaitItem()
            awaitComplete()
        }

        repository.getProductCategory(categoryId).test {
            awaitItem()
            awaitComplete()
        }

        repository.searchProducts(query, 0, 10).test {
            awaitItem()
            awaitComplete()
        }

        coVerify(exactly = 1) { remoteDataSource.getProductDetails(productId) }
        coVerify(exactly = 1) { remoteDataSource.getProductCategory(categoryId) }
        coVerify(exactly = 1) { remoteDataSource.searchProducts(query, 0, 10) }
    }
}
