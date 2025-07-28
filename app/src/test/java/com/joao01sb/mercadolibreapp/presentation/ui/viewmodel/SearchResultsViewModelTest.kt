package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockProductData
import com.joao01sb.mercadolibreapp.domain.usecase.SearchProductsUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
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
class SearchResultsViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var searchProductsUseCase: SearchProductsUseCase
    private lateinit var viewModel: SearchResultsViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val query = "iphone"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = mockk(relaxed = true)
        searchProductsUseCase = mockk()

        every { savedStateHandle.get<String>("query") } returns query

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `should initialize with loading state and search products successfully`() = runTest {
        val mockProducts = MockProductData.mockProducts
        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(mockProducts))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is UiState.Loading)

            testDispatcher.scheduler.advanceUntilIdle()
            val successState = expectMostRecentItem()
            assertTrue(successState is UiState.Success)

            with((successState as UiState.Success).data) {
                assertEquals(query, this.query)
                assertEquals(mockProducts, products)
                assertTrue(hasSearched)
                assertEquals(mockProducts.size, products.size)
            }
        }

        verify { Log.d("SearchResultsViewModel", "init: Initializing SearchResultsViewModel with query='$query'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Starting search for query='$query'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Setting loading state") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Success - Found ${mockProducts.size} products") }
        coVerify { searchProductsUseCase(query) }
    }

    @Test
    fun `should handle search products use case error`() = runTest {
        val errorMessage = "Network connection failed"
        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Error(errorMessage))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is UiState.Loading)

            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = expectMostRecentItem()
            assertTrue(errorState is UiState.Error)
            assertEquals(errorMessage, (errorState as UiState.Error).message)
        }

        verify { Log.d("SearchResultsViewModel", "init: Initializing SearchResultsViewModel with query='$query'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Starting search for query='$query'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Setting loading state") }
        verify { Log.e("SearchResultsViewModel", "searchProducts: Error - $errorMessage") }
        coVerify { searchProductsUseCase(query) }
    }

    @Test
    fun `should handle empty query validation`() = runTest {
        val emptyQuery = ""
        every { savedStateHandle.get<String>("query") } returns emptyQuery

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        viewModel.searchProducts(emptyQuery)

        viewModel.uiState.test {
            val errorState = expectMostRecentItem()
            assertTrue(errorState is UiState.Error)
            assertEquals("Search query cannot be empty", (errorState as UiState.Error).message)
        }

        verify { Log.w("SearchResultsViewModel", "searchProducts: Query is blank, emitting error") }
        coVerify(exactly = 0) { searchProductsUseCase(any()) }
    }

    @Test
    fun `should handle blank query validation`() = runTest {
        val blankQuery = "   "

        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(emptyList()))
        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        viewModel.searchProducts(blankQuery)

        viewModel.uiState.test {
            val errorState = expectMostRecentItem()
            assertTrue(errorState is UiState.Error)
            assertEquals("Search query cannot be empty", (errorState as UiState.Error).message)
        }

        verify { Log.w("SearchResultsViewModel", "searchProducts: Query is blank, emitting error") }
        coVerify(exactly = 0) { searchProductsUseCase(blankQuery) }
    }

    @Test
    fun `should handle successful search with empty results`() = runTest {
        val emptyResults = emptyList<com.joao01sb.mercadolibreapp.domain.model.Product>()
        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(emptyResults))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val successState = expectMostRecentItem()
            assertTrue(successState is UiState.Success)

            with((successState as UiState.Success).data) {
                assertEquals(query, this.query)
                assertTrue(products.isEmpty())
                assertTrue(hasSearched)
            }
        }

        verify { Log.d("SearchResultsViewModel", "searchProducts: Success - Found 0 products") }
        coVerify { searchProductsUseCase(query) }
    }

    @Test
    fun `retrySearch should call searchProducts with current query`() = runTest {
        val mockProducts = MockProductData.mockProducts
        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(mockProducts))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        clearMocks(searchProductsUseCase, answers = false)

        viewModel.retrySearch()

        testDispatcher.scheduler.advanceUntilIdle()

        verify { Log.d("SearchResultsViewModel", "retrySearch: User requested retry for query='$query'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Starting search for query='$query'") }
        coVerify(exactly = 1) { searchProductsUseCase(query, 0, 50) }
    }

    @Test
    fun `currentQuery should return correct query value`() = runTest {
        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(emptyList()))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        assertEquals(query, viewModel.currentQuery)
    }

    @Test
    fun `should handle multiple search requests correctly`() = runTest {
        val firstResults = MockProductData.mockProducts.take(2)
        val secondQuery = "samsung"
        val secondResults = MockProductData.mockProducts.takeLast(3)

        coEvery { searchProductsUseCase(query) } returns flowOf(ResultUseCase.Success(firstResults))
        coEvery { searchProductsUseCase(secondQuery) } returns flowOf(ResultUseCase.Success(secondResults))

        viewModel = SearchResultsViewModel(savedStateHandle, searchProductsUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchProducts(secondQuery)

        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val finalState = expectMostRecentItem()
            assertTrue(finalState is UiState.Success)

            with((finalState as UiState.Success).data) {
                assertEquals(secondQuery, this.query)
                assertEquals(secondResults, products)
                assertTrue(hasSearched)
            }
        }

        verify { Log.d("SearchResultsViewModel", "searchProducts: Starting search for query='$secondQuery'") }
        verify { Log.d("SearchResultsViewModel", "searchProducts: Success - Found ${secondResults.size} products") }
        coVerify { searchProductsUseCase(query) }
        coVerify { searchProductsUseCase(secondQuery) }
    }
}
