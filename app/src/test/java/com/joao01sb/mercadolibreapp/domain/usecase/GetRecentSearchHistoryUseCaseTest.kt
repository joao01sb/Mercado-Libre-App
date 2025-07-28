package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.mock.MockSearchHistory
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetRecentSearchHistoryUseCaseTest {

    private lateinit var repository: SearchHistoryRepository
    private lateinit var useCase: GetRecentSearchHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetRecentSearchHistoryUseCase(repository)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should return success with mapped queries when repository has data`() = runTest {
        val mockHistoryList = MockSearchHistory.mockSearchHistoryDomain
        val expectedQueries = listOf("iphone 13", "samsung galaxy", "macbook pro")

        coEvery { repository.getRecentSearchHistory() } returns flowOf(mockHistoryList)

        useCase.invoke().test {
            val result = awaitItem()

            assertTrue("Should be Success", result is ResultUseCase.Success)
            assertEquals(expectedQueries, (result as ResultUseCase.Success).data)
            assertEquals(3, result.data.size)
            assertEquals("iphone 13", result.data[0])
            assertEquals("samsung galaxy", result.data[1])
            assertEquals("macbook pro", result.data[2])

            awaitComplete()
        }

        coVerify(exactly = 1) { repository.getRecentSearchHistory() }

        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Starting to fetch recent search history") }
        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Received 3 history items from repository") }
        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Successfully mapped 3 search queries") }
    }

    @Test
    fun `invoke should return success with empty list when repository has no data`() = runTest {
        val emptyHistoryList = emptyList<SearchHistory>()

        coEvery { repository.getRecentSearchHistory() } returns flowOf(emptyHistoryList)

        useCase.invoke().test {
            val result = awaitItem()

            assertTrue("Should be Success", result is ResultUseCase.Success)
            assertEquals(emptyList<String>(), (result as ResultUseCase.Success).data)
            assertTrue("Should return empty list", result.data.isEmpty())

            awaitComplete()
        }

        coVerify(exactly = 1) { repository.getRecentSearchHistory() }

        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Starting to fetch recent search history") }
        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Received 0 history items from repository") }
        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Successfully mapped 0 search queries") }
    }

    @Test
    fun `invoke should return error when repository throws IOException`() = runTest {
        val exception = IOException("Database connection failed")

        coEvery { repository.getRecentSearchHistory() } returns flow {
            throw exception
        }

        useCase.invoke().test {
            val result = awaitItem()

            assertTrue("Should be Error", result is ResultUseCase.Error)
            assertEquals("An error occurred while fetching search history: Database connection failed",
                (result as ResultUseCase.Error).message)

            awaitComplete()
        }

        coVerify(exactly = 1) { repository.getRecentSearchHistory() }

        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Starting to fetch recent search history") }
        verify { Log.e("GetRecentSearchHistoryUseCase", "invoke: Error fetching search history", exception) }
    }

    @Test
    fun `invoke should return error when repository throws RuntimeException`() = runTest {
        val exception = RuntimeException("Unexpected error occurred")

        coEvery { repository.getRecentSearchHistory() } returns flow {
            throw exception
        }

        useCase.invoke().test {
            val result = awaitItem()

            assertTrue("Should be Error", result is ResultUseCase.Error)
            assertEquals("An error occurred while fetching search history: Unexpected error occurred",
                (result as ResultUseCase.Error).message)

            awaitComplete()
        }

        coVerify(exactly = 1) { repository.getRecentSearchHistory() }

        verify { Log.d("GetRecentSearchHistoryUseCase", "invoke: Starting to fetch recent search history") }
        verify { Log.e("GetRecentSearchHistoryUseCase", "invoke: Error fetching search history", exception) }
    }
}
