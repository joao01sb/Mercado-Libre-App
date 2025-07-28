package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.model.Category
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

class GetProductCategoryUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var useCase: GetProductCategoryUseCase

    @Before
    fun setUp() {
        productRepository = mockk()
        useCase = GetProductCategoryUseCase(productRepository)

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
    fun `invoke should return success when repository returns category successfully`() = runTest {
        val categoryId = "MLA5725"
        val mockCategory = MockProductData.mockCategory

        coEvery { productRepository.getProductCategory(categoryId) } returns flowOf(mockCategory)

        useCase.invoke(categoryId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            assertEquals(mockCategory, (result as ResultUseCase.Success).data)
            awaitComplete()
        }

        verify { Log.d("GetProductCategoryUseCase", "invoke: Starting to get category for categoryId='$categoryId'") }
        coVerify { productRepository.getProductCategory(categoryId) }
    }

    @Test
    fun `invoke should return success when repository returns valid category with all fields`() = runTest {
        val categoryId = "MLA109027"
        val mockCategory = Category(
            id = "MLA109027",
            name = "Zapatos",
            picture = "https://listado.mercadolibre.com.ar/zapatos",
            totalItems = 100
        )

        coEvery { productRepository.getProductCategory(categoryId) } returns flowOf(mockCategory)

        useCase.invoke(categoryId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Success)
            with((result as ResultUseCase.Success).data) {
                assertEquals("MLA109027", id)
                assertEquals("Zapatos", name)
                assertEquals("https://listado.mercadolibre.com.ar/zapatos", picture)
                assertEquals(100, totalItems)
            }
            awaitComplete()
        }

        verify { Log.d("GetProductCategoryUseCase", "invoke: Starting to get category for categoryId='$categoryId'") }
        coVerify { productRepository.getProductCategory(categoryId) }
    }

    @Test
    fun `invoke should return error when categoryId is empty`() = runTest {
        val emptyCategoryId = ""

        useCase.invoke(emptyCategoryId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Category ID cannot be empty", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("GetProductCategoryUseCase", "invoke: Starting to get category for categoryId='$emptyCategoryId'") }
        coVerify(exactly = 0) { productRepository.getProductCategory(any()) }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val categoryId = "MLA5725"
        val errorMessage = "Network error"

        coEvery { productRepository.getProductCategory(categoryId) } throws RuntimeException(errorMessage)

        useCase.invoke(categoryId).test {
            val result = awaitItem()
            assertTrue(result is ResultUseCase.Error)
            assertEquals("Failed to get category", (result as ResultUseCase.Error).message)
            awaitComplete()
        }

        verify { Log.d("GetProductCategoryUseCase", "invoke: Starting to get category for categoryId='$categoryId'") }
        coVerify { productRepository.getProductCategory(categoryId) }
    }
}
