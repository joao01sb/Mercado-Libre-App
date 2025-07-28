package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
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

class GetProductDescriptionUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var useCase: GetProductDescriptionUseCase

    @Before
    fun setUp() {
        productRepository = mockk()
        useCase = GetProductDescriptionUseCase(productRepository)

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
    fun `invoke should return success when repository returns product description successfully`() = runTest {
        val productId = "MLA2005705454"
        val mockDescription = MockProductData.mockProductDescription

        coEvery { productRepository.getProductDescription(productId) } returns flowOf(mockDescription)

        useCase.invoke(productId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            assertEquals(mockDescription, (result as ResultUseCase.Success).data)
            awaitComplete()
        }

        verify { Log.d("GetProductDescriptionUseCase", "invoke: Getting product description for productId='$productId'") }
        coVerify { productRepository.getProductDescription(productId) }
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

        verify { Log.d("GetProductDescriptionUseCase", "invoke: Getting product description for productId='$emptyProductId'") }
        coVerify(exactly = 0) { productRepository.getProductDescription(any()) }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val productId = "MLA2005705454"
        val errorMessage = "Network connection failed"

        coEvery { productRepository.getProductDescription(productId) } throws RuntimeException(errorMessage)

        useCase.invoke(productId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Failed to get product description", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("GetProductDescriptionUseCase", "invoke: Getting product description for productId='$productId'") }
        coVerify { productRepository.getProductDescription(productId) }
    }
}
