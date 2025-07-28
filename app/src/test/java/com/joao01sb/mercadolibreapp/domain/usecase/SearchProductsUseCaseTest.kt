package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchProductsUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var useCase: SearchProductsUseCase

    @Before
    fun setUp() {
        productRepository = mockk()
        useCase = SearchProductsUseCase(productRepository)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should return success when repository returns products successfully`() = runTest {
        val query = "iphone"
        val offset = 0
        val limit = 50
        val mockProducts = MockProductData.mockProducts

        coEvery { productRepository.searchProducts(query, offset, limit) } returns flowOf(mockProducts)

        useCase.invoke(query, offset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            assertEquals(mockProducts, (result as ResultUseCase.Success).data)
            awaitComplete()
        }

        verify { Log.d("SearchProductsUseCase", "invoke: Searching products with query='$query', offset=$offset, limit=$limit") }
        coVerify { productRepository.searchProducts(query, offset, limit) }
    }

    @Test
    fun `invoke should return success with default pagination parameters`() = runTest {
        val query = "zapatillas"
        val product = MockProductData.mockProducts.first()
        val mockProducts = listOf(
            product.copy(id = "MLA1", title = "Zapatillas Nike"),
            product.copy(id = "MLA2", title = "Zapatillas Adidas")
        )

        coEvery { productRepository.searchProducts(query, 0, 50) } returns flowOf(mockProducts)

        useCase.invoke(query).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            assertEquals(2, (result as ResultUseCase.Success).data.size)
            assertEquals("Zapatillas Nike", result.data[0].title)
            assertEquals("Zapatillas Adidas", result.data[1].title)
            awaitComplete()
        }

        verify { Log.d("SearchProductsUseCase", "invoke: Searching products with query='$query', offset=0, limit=50") }
        coVerify { productRepository.searchProducts(query, 0, 50) }
    }

    @Test
    fun `invoke should return error when query is empty`() = runTest {
        val emptyQuery = ""
        val offset = 0
        val limit = 50

        useCase.invoke(emptyQuery, offset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Search query cannot be empty", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("SearchProductsUseCase", "invoke: Searching products with query='$emptyQuery', offset=$offset, limit=$limit") }
        coVerify(exactly = 0) { productRepository.searchProducts(any(), any(), any()) }
    }

    @Test
    fun `invoke should return error when offset is negative`() = runTest {
        val query = "arroz"
        val invalidOffset = -1
        val limit = 50

        useCase.invoke(query, invalidOffset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Offset cannot be negative", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("SearchProductsUseCase", "invoke: Searching products with query='$query', offset=$invalidOffset, limit=$limit") }
        coVerify(exactly = 0) { productRepository.searchProducts(any(), any(), any()) }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val query = "notebook"
        val offset = 0
        val limit = 50
        val errorMessage = "Network connection timeout"

        coEvery { productRepository.searchProducts(query, offset, limit) } throws RuntimeException(errorMessage)

        useCase.invoke(query, offset, limit).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Failed to search products", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("SearchProductsUseCase", "invoke: Searching products with query='$query', offset=$offset, limit=$limit") }
        coVerify { productRepository.searchProducts(query, offset, limit) }
    }
}
