package com.joao01sb.mercadolibreapp.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockSearchHistory
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory
import com.joao01sb.mercadolibreapp.domain.usecase.GetRecentSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.SaveSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
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
class SearchViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
    private lateinit var saveSearchHistoryUseCase: SaveSearchHistoryUseCase
    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val initialQuery = "iphone"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = mockk(relaxed = true)
        getRecentSearchHistoryUseCase = mockk()
        saveSearchHistoryUseCase = mockk()

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
    fun `should initialize with loading state and load search history successfully`() = runTest {
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is UiState.Loading)

            testDispatcher.scheduler.advanceUntilIdle()
            val successState = expectMostRecentItem()
            assertTrue(successState is UiState.Success)

            with((successState as UiState.Success).data) {
                assertEquals(initialQuery, searchQuery)
                assertEquals(mockSearchHistory, recentSearches)
                assertEquals(mockSearchHistory.size, recentSearches.size)
            }
        }

        verify { Log.d("SearchViewModel", "init: Initializing SearchViewModel") }
        verify { Log.d("SearchViewModel", "init: Initial query is '$initialQuery'") }
        verify { Log.d("SearchViewModel", "loadSearchHistory: Starting to load recent searches") }
        verify { Log.d("SearchViewModel", "loadSearchHistory: Success - Found ${mockSearchHistory.size} recent searches") }
        coVerify { getRecentSearchHistoryUseCase() }
    }

    @Test
    fun `should initialize with empty initial query and load search history successfully`() = runTest {
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        every { savedStateHandle.get<String>("initialQuery") } returns null
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val successState = expectMostRecentItem()
            assertTrue(successState is UiState.Success)

            with((successState as UiState.Success).data) {
                assertEquals("", searchQuery)
                assertEquals(mockSearchHistory, recentSearches)
            }
        }

        verify { Log.d("SearchViewModel", "init: Initial query is ''") }
        coVerify { getRecentSearchHistoryUseCase() }
    }

    @Test
    fun `should handle search history loading error`() = runTest {
        val errorMessage = "Database connection failed"
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Error(errorMessage))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = expectMostRecentItem()
            assertTrue(errorState is UiState.Error)
            assertEquals(errorMessage, (errorState as UiState.Error).message)
        }

        verify { Log.d("SearchViewModel", "loadSearchHistory: Starting to load recent searches") }
        verify { Log.e("SearchViewModel", "loadSearchHistory: Error - $errorMessage") }
        coVerify { getRecentSearchHistoryUseCase() }
    }

    @Test
    fun `changedSearchProducts should update query in existing success state`() = runTest {
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        val newQuery = "samsung galaxy"
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.changedSearchProducts(newQuery)

        viewModel.uiState.test {
            val currentState = expectMostRecentItem()
            assertTrue(currentState is UiState.Success)

            with((currentState as UiState.Success).data) {
                assertEquals(newQuery, searchQuery)
                assertEquals(mockSearchHistory, recentSearches)
            }
        }

        verify { Log.d("SearchViewModel", "changedSearchProducts: Query changed to '$newQuery'") }
        verify { Log.d("SearchViewModel", "changedSearchProducts: Updating existing success state") }
    }

    @Test
    fun `changedSearchProducts should handle blank query correctly`() = runTest {
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        val blankQuery = "   "
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.changedSearchProducts(blankQuery)

        viewModel.uiState.test {
            val currentState = expectMostRecentItem()
            assertTrue(currentState is UiState.Success)

            with((currentState as UiState.Success).data) {
                assertEquals("", searchQuery)
                assertEquals(mockSearchHistory, recentSearches)
            }
        }

        verify { Log.d("SearchViewModel", "changedSearchProducts: Query changed to '$blankQuery'") }
        verify { Log.d("SearchViewModel", "changedSearchProducts: Updating existing success state") }
    }

    @Test
    fun `changedSearchProducts should create new success state when current state is not success`() = runTest {
        val newQuery = "notebook"
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Error("Some error"))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.changedSearchProducts(newQuery)

        viewModel.uiState.test {
            val currentState = expectMostRecentItem()
            assertTrue(currentState is UiState.Success)

            with((currentState as UiState.Success).data) {
                assertEquals(newQuery, searchQuery)
                assertTrue(recentSearches.isEmpty())
            }
        }

        verify { Log.d("SearchViewModel", "changedSearchProducts: Query changed to '$newQuery'") }
        verify { Log.d("SearchViewModel", "changedSearchProducts: Creating new success state") }
    }

    @Test
    fun `saveSearch should save search successfully`() = runTest {
        val queryToSave = "macbook pro"
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))
        coEvery { saveSearchHistoryUseCase(queryToSave) } returns ResultUseCase.Success(Unit)

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveSearch(queryToSave)
        testDispatcher.scheduler.advanceUntilIdle()

        verify { Log.d("SearchViewModel", "saveSearch: Saving search query '$queryToSave'") }
        verify { Log.d("SearchViewModel", "saveSearch: Successfully saved search query") }
        coVerify { saveSearchHistoryUseCase(queryToSave) }
    }

    @Test
    fun `saveSearch should handle save error and update UI state`() = runTest {
        val queryToSave = "tablet"
        val errorMessage = "Failed to save search"
        val mockSearchHistory = MockSearchHistory.mockSearchHistoryToString
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(mockSearchHistory))
        coEvery { saveSearchHistoryUseCase(queryToSave) } returns ResultUseCase.Error(errorMessage)

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveSearch(queryToSave)

        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = expectMostRecentItem()
            assertTrue(errorState is UiState.Error)
            assertEquals(errorMessage, (errorState as UiState.Error).message)
        }

        verify { Log.d("SearchViewModel", "saveSearch: Saving search query '$queryToSave'") }
        verify { Log.e("SearchViewModel", "saveSearch: Error - $errorMessage") }
        coVerify { saveSearchHistoryUseCase(queryToSave) }
    }

    @Test
    fun `should handle empty search history successfully`() = runTest {
        val emptyHistory = listOf<String>()
        every { savedStateHandle.get<String>("initialQuery") } returns initialQuery
        coEvery { getRecentSearchHistoryUseCase() } returns flowOf(ResultUseCase.Success(emptyHistory))

        viewModel = SearchViewModel(savedStateHandle, getRecentSearchHistoryUseCase, saveSearchHistoryUseCase)

        viewModel.uiState.test {
            skipItems(1)
            testDispatcher.scheduler.advanceUntilIdle()
            val successState = expectMostRecentItem()
            assertTrue(successState is UiState.Success)

            with((successState as UiState.Success).data) {
                assertEquals(initialQuery, searchQuery)
                assertTrue(recentSearches.isEmpty())
            }
        }

        verify { Log.d("SearchViewModel", "loadSearchHistory: Success - Found 0 recent searches") }
        coVerify { getRecentSearchHistoryUseCase() }
    }
}
