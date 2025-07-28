package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductCategoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDescriptionUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDetailsUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.util.ProductSerializationUtil
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase
    private lateinit var getProductDescriptionUseCase: GetProductDescriptionUseCase
    private lateinit var getProductCategoryUseCase: GetProductCategoryUseCase
    private lateinit var viewModel: ProductDetailViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val productId = "MLA2005705454"
    private val query = "iphone"
    private val productJson = """{"id":"$productId","title":"iPhone 13"}"""

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = mockk(relaxed = true)
        getProductDetailsUseCase = mockk()
        getProductDescriptionUseCase = mockk()
        getProductCategoryUseCase = mockk()

        every { savedStateHandle.get<String>("productId") } returns productId
        every { savedStateHandle.get<String>("query") } returns query
        every { savedStateHandle.get<String>("productJson") } returns productJson

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0

        mockkObject(ProductSerializationUtil)
        every { ProductSerializationUtil.deserializeProduct(productJson) } returns MockProductData.mockProducts.first()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun createViewModel(): ProductDetailViewModel {
        coEvery { getProductDetailsUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        return ProductDetailViewModel(
            savedStateHandle,
            getProductDetailsUseCase,
            getProductDescriptionUseCase,
            getProductCategoryUseCase
        )
    }

    @Test
    fun `should initialize with loading state and load all product data successfully`() = runTest {
        viewModel = createViewModel()

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState is UiState.Success)

            with((initialState as UiState.Success).data) {
                assertNotNull(baseProduct)
                assertEquals(productId, baseProduct?.id)
            }

            testDispatcher.scheduler.advanceUntilIdle()

            val finalState = expectMostRecentItem()
            assertTrue(finalState is UiState.Success)

            with((finalState as UiState.Success).data) {
                assertNotNull(baseProduct)
                assertNotNull(productDetail)
                assertNotNull(description)
                assertNotNull(category)
                assertEquals(productId, baseProduct?.id)
                assertEquals(MockProductData.mockProductDetail.id, productDetail?.id)
                assertEquals(MockProductData.mockProductDescription.plainText, description)
                assertEquals(MockProductData.mockCategory.name, category?.name)
            }

            coVerify { getProductDetailsUseCase(productId) }
            coVerify { getProductDescriptionUseCase(productId) }
            coVerify { getProductCategoryUseCase(productId) }
        }
    }

    @Test
    fun `should handle successful product details loading`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertTrue(state is UiState.Success)

            testDispatcher.scheduler.advanceUntilIdle()
            val finalState = expectMostRecentItem()
            assertTrue(finalState is UiState.Success)

            with((finalState as UiState.Success).data) {
                assertNotNull(productDetail)
                assertEquals(MockProductData.mockProductDetail.id, productDetail?.id)
            }
        }

        verify { Log.d("ProductDetailViewModel", "loadProductDetails: Success - ProductDetail loaded") }
    }

    @Test
    fun `should handle product details loading error`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Error("Network error"))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNull(productDetail)
                assertNotNull(baseProduct)
            }
        }

        verify { Log.d("ProductDetailViewModel", "loadProductDetails: Error - Network error") }
    }

    @Test
    fun `should handle successful product description loading`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertEquals(MockProductData.mockProductDescription.plainText, description)
            }
        }

        verify { Log.d("ProductDetailViewModel", "loadProductDescription: Success - Description loaded") }
    }

    @Test
    fun `should handle product description loading error`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(productId) } returns flowOf(ResultUseCase.Error("Description not found"))
        coEvery { getProductCategoryUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNull(description)
            }
        }

        verify { Log.w("ProductDetailViewModel", "loadProductDescription: Error - Description not found") }
    }

    @Test
    fun `should handle successful category loading`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNotNull(category)
                assertEquals(MockProductData.mockCategory.name, category?.name)
            }
        }

        verify { Log.d("ProductDetailViewModel", "loadProductCategory: Success - Category loaded: ${MockProductData.mockCategory.name}") }
    }

    @Test
    fun `should handle category loading error`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(productId) } returns flowOf(ResultUseCase.Error("Category not found"))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNull(category)
            }
        }

        verify { Log.w("ProductDetailViewModel", "loadProductCategory: Error - Category not found") }
    }

    @Test
    fun `should handle null base product when deserialization fails`() = runTest {
        every { ProductSerializationUtil.deserializeProduct(productJson) } returns null

        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(any()) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState is UiState.Success)

            with((initialState as UiState.Success).data) {
                assertNull(baseProduct)
            }

            testDispatcher.scheduler.advanceUntilIdle()
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) { getProductCategoryUseCase(any()) }
    }

    @Test
    fun `retryLoadProduct should reinitialize all data`() = runTest {
        viewModel = createViewModel()

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retryLoadProduct()

        verify { Log.d("ProductDetailViewModel", "retryLoadProduct: User requested retry") }

        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(atLeast = 2) { getProductDetailsUseCase(productId) }
    }

    @Test
    fun `currentQuery should return correct query value`() = runTest {
        viewModel = createViewModel()

        assertEquals(query, viewModel.currentQuery)
    }

    @Test
    fun `should handle only product details use case error while others succeed`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Error("Product details network error"))
        coEvery { getProductDescriptionUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNotNull(baseProduct)
                assertNull(productDetail)
                assertEquals(MockProductData.mockCategory.name, category?.name)
            }
        }

        verify { Log.d("ProductDetailViewModel", "loadProductDetails: Error - Product details network error") }
        coVerify { getProductDetailsUseCase(productId) }
        coVerify(exactly = 0) { getProductDescriptionUseCase(productId) }
        coVerify { getProductCategoryUseCase(productId) }
    }

    @Test
    fun `should handle only product description use case error while others succeed`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(productId) } returns flowOf(ResultUseCase.Error("Description service unavailable"))
        coEvery { getProductCategoryUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockCategory))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNotNull(baseProduct)
                assertNotNull(productDetail)
                assertNull(description)
                assertEquals(MockProductData.mockCategory.name, category?.name)
            }
        }

        verify { Log.w("ProductDetailViewModel", "loadProductDescription: Error - Description service unavailable") }
        coVerify { getProductDetailsUseCase(productId) }
        coVerify { getProductDescriptionUseCase(productId) }
        coVerify { getProductCategoryUseCase(productId) }
    }

    @Test
    fun `should handle only product category use case error while others succeed`() = runTest {
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDetail))
        coEvery { getProductDescriptionUseCase(productId) } returns flowOf(ResultUseCase.Success(MockProductData.mockProductDescription))
        coEvery { getProductCategoryUseCase(productId) } returns flowOf(ResultUseCase.Error("Category database error"))

        viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailsUseCase, getProductDescriptionUseCase, getProductCategoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem()
            assertTrue(state is UiState.Success)

            with((state as UiState.Success).data) {
                assertNotNull(baseProduct)
                assertNotNull(productDetail)
                assertEquals(MockProductData.mockProductDescription.plainText, description)
                assertNull(category)
            }
        }

        verify { Log.w("ProductDetailViewModel", "loadProductCategory: Error - Category database error") }
        coVerify { getProductDetailsUseCase(productId) }
        coVerify { getProductDescriptionUseCase(productId) }
        coVerify { getProductCategoryUseCase(productId) }
    }
}
