package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
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

class GetProductDetailsUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var useCase: GetProductDetailsUseCase

    @Before
    fun setUp() {
        productRepository = mockk()
        useCase = GetProductDetailsUseCase(productRepository)

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
    fun `invoke should return success when repository returns product details successfully`() = runTest {
        val productId = "MLA2005705454"
        val mockProductDetail = MockProductData.mockProductDetail

        coEvery { productRepository.getProductDetails(productId) } returns flowOf(mockProductDetail)

        useCase.invoke(productId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            assertEquals(mockProductDetail, (result as ResultUseCase.Success).data)
            awaitComplete()
        }

        verify { Log.d("GetProductDetailsUseCase", "invoke: Getting product details for productId='$productId'") }
        coVerify { productRepository.getProductDetails(productId) }
    }

    @Test
    fun `invoke should return error when productId is empty`() = runTest {
        val emptyProductId = ""

        useCase.invoke(emptyProductId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Product ID cannot be empty", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("GetProductDetailsUseCase", "invoke: Getting product details for productId='$emptyProductId'") }
        coVerify(exactly = 0) { productRepository.getProductDetails(any()) }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val productId = "MLA2005705454"
        val errorMessage = "Database connection error"

        coEvery { productRepository.getProductDetails(productId) } throws RuntimeException(errorMessage)

        useCase.invoke(productId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Failed to get product details", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("GetProductDetailsUseCase", "invoke: Getting product details for productId='$productId'") }
        coVerify { productRepository.getProductDetails(productId) }
    }
}
