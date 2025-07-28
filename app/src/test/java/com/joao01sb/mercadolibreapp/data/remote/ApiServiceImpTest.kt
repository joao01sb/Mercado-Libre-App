package com.joao01sb.mercadolibreapp.data.remote

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.ApiServiceMockData
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class ApiServiceImpTest {

    private lateinit var mockContext: Context
    private lateinit var mockAssetManager: AssetManager
    private lateinit var apiService: ApiServiceImp

    @Before
    fun setUp() {
        mockContext = mockk(relaxed = true)
        mockAssetManager = mockk(relaxed = true)

        every { mockContext.assets } returns mockAssetManager

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0

        apiService = ApiServiceImp(mockContext)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `searchProducts should return SearchResponse when using real asset data`() = runTest {
        val query = ApiServiceMockData.TestData.VALID_QUERY
        val mockInputStream = ApiServiceMockData.VALID_SEARCH_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.searchFile(query)) } returns mockInputStream

        apiService.searchProducts(query, 0, 50).test {
            val result = awaitItem()

            assertNotNull(result)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_SITE_ID, result.siteId)
            assertEquals(query, result.query)
            assertEquals(1, result.resultProducts.size)
            assertEquals(ApiServiceMockData.TestData.VALID_PRODUCT_ID, result.resultProducts[0].id)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_PRODUCT_TITLE, result.resultProducts[0].title)

            awaitComplete()
        }

        verify { Log.d("ApiServiceImp", ApiServiceMockData.LogMessages.searchingProducts(query)) }
    }

    @Test
    fun `searchProducts should throw FileNotFoundException when file does not exist`() = runTest {
        val query = ApiServiceMockData.TestData.NONEXISTENT_QUERY

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.searchFile(query)) } throws FileNotFoundException()

        apiService.searchProducts(query, 0, 50).test {
            val exception = awaitError()

            assert(exception is FileNotFoundException)
            assert(exception.message?.contains(ApiServiceMockData.ExceptionMessages.SEARCH_PRODUCTS_MOCK_UNAVAILABLE) == true)
        }

        verify { Log.e("ApiServiceImp", ApiServiceMockData.LogMessages.errorSearchingProducts(query), any()) }
    }

    @Test
    fun `getProductDetails should return ProductDetailResponse when using real asset data`() = runTest {
        val productId = ApiServiceMockData.TestData.VALID_PRODUCT_ID
        val mockInputStream = ApiServiceMockData.VALID_PRODUCT_DETAIL_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.productDetailFile(productId)) } returns mockInputStream

        apiService.getProductDetails(productId).test {
            val result = awaitItem()

            assertNotNull(result)
            assertEquals(productId, result.id)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_PRODUCT_TITLE, result.title)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_PRODUCT_PRICE, result.price)

            awaitComplete()
        }

        verify { Log.d("ApiServiceImp", ApiServiceMockData.LogMessages.gettingProductDetails(productId)) }
    }

    @Test
    fun `getProductDetails should throw SerializationException when JSON is malformed`() = runTest {
        val productId = ApiServiceMockData.TestData.VALID_PRODUCT_ID
        val mockInputStream = ApiServiceMockData.MALFORMED_PRODUCT_DETAIL_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.productDetailFile(productId)) } returns mockInputStream

        apiService.getProductDetails(productId).test {
            val exception = awaitError()

            assert(exception is SerializationException)
            assert(exception.message?.contains(ApiServiceMockData.ExceptionMessages.PRODUCT_DETAILS_INVALID_JSON) == true)
        }

        verify { Log.e("ApiServiceImp", ApiServiceMockData.LogMessages.errorGettingProductDetails(productId), any()) }
    }

    @Test
    fun `getProductDescription should return ProductDescriptionResponse when using real asset data`() = runTest {
        val productId = ApiServiceMockData.TestData.VALID_PRODUCT_ID
        val mockInputStream = ApiServiceMockData.VALID_DESCRIPTION_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.productDescriptionFile(productId)) } returns mockInputStream

        apiService.getProductDescription(productId).test {
            val result = awaitItem()

            assertNotNull(result)
            assertNotNull(result.text)
            assertNotNull(result.plainText)

            awaitComplete()
        }

        verify { Log.d("ApiServiceImp", ApiServiceMockData.LogMessages.gettingProductDescription(productId)) }
    }

    @Test
    fun `getProductDescription should throw SerializationException when JSON is malformed`() = runTest {
        val productId = ApiServiceMockData.TestData.VALID_PRODUCT_ID
        val mockInputStream = ApiServiceMockData.MALFORMED_DESCRIPTION_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.productDescriptionFile(productId)) } returns mockInputStream

        apiService.getProductDescription(productId).test {
            val exception = awaitError()

            assert(exception is SerializationException)
            assert(exception.message?.contains("Failed to get product description - Invalid JSON format") == true)
        }

        verify { Log.e("ApiServiceImp", ApiServiceMockData.LogMessages.errorGettingProductDescription(productId), any()) }
    }

    @Test
    fun `getProductCategory should return CategoryResponse when using real asset data`() = runTest {
        val categoryId = ApiServiceMockData.TestData.VALID_CATEGORY_ID
        val mockInputStream = ApiServiceMockData.VALID_CATEGORY_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.categoryFile(categoryId)) } returns mockInputStream

        apiService.getProductCategory(categoryId).test {
            val result = awaitItem()

            assertNotNull(result)
            assertEquals(categoryId, result.id)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_CATEGORY_NAME, result.name)
            assertEquals(ApiServiceMockData.TestData.EXPECTED_CATEGORY_TOTAL_ITEMS, result.totalItemsInThisCategory)

            awaitComplete()
        }

        verify { Log.d("ApiServiceImp", ApiServiceMockData.LogMessages.gettingCategory(categoryId)) }
    }

    @Test
    fun `getProductCategory should throw SerializationException when JSON is malformed`() = runTest {
        val categoryId = ApiServiceMockData.TestData.VALID_CATEGORY_ID
        val mockInputStream = ApiServiceMockData.MALFORMED_CATEGORY_JSON.byteInputStream()

        every { mockAssetManager.open(ApiServiceMockData.AssetPaths.categoryFile(categoryId)) } returns mockInputStream

        apiService.getProductCategory(categoryId).test {
            val exception = awaitError()

            assert(exception is SerializationException)
            assert(exception.message?.contains("Failed to get category - Invalid JSON format") == true)
        }

        verify { Log.e("ApiServiceImp", ApiServiceMockData.LogMessages.errorGettingCategory(categoryId), any()) }
    }
}
