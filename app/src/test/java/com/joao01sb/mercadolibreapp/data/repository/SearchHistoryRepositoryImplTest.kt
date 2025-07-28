package com.joao01sb.mercadolibreapp.data.repository

import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import com.joao01sb.mercadolibreapp.data.local.entity.SearchHistoryEntity
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
import java.sql.SQLException
import com.joao01sb.mercadolibreapp.data.mock.MockSearchHistory

class SearchHistoryRepositoryImplTest {

    private lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var repository: SearchHistoryRepositoryImpl

    @Before
    fun setUp() {
        searchHistoryDao = mockk(relaxed = true)
        repository = SearchHistoryRepositoryImpl(searchHistoryDao)
    }

    @Test
    fun `saveSearchHistory should insert search entity successfully`() = runTest {
        val query = "iphone 13"

        coEvery { searchHistoryDao.insertSearchHistory(any()) } returns Unit

        repository.saveSearchHistory(query)

        coVerify(exactly = 1) {
            searchHistoryDao.insertSearchHistory(
                match { entity -> entity.query == query }
            )
        }
    }

    @Test
    fun `getRecentSearchHistory should return mapped list of search history`() = runTest {
        val mockEntities = MockSearchHistory.mockSearchHistoryEntities
        val expectedSearchHistory = MockSearchHistory.mockSearchHistoryDomain

        coEvery { searchHistoryDao.getRecentSearchHistory() } returns flowOf(mockEntities)

        repository.getRecentSearchHistory().test {
            val actual = awaitItem()

            assertEquals(expectedSearchHistory.size, actual.size)
            assertEquals(expectedSearchHistory[0].query, actual[0].query)
            assertEquals(expectedSearchHistory[1].query, actual[1].query)

            awaitComplete()
        }

        coVerify(exactly = 1) { searchHistoryDao.getRecentSearchHistory() }
    }

    @Test(expected = SQLException::class)
    fun `saveSearchHistory should handle database constraint violation`() = runTest {
        val query = "duplicate query"
        val dbError = SQLException("UNIQUE constraint failed")

        coEvery { searchHistoryDao.insertSearchHistory(any()) } throws dbError

        try {
            repository.saveSearchHistory(query)
        } catch (e: SQLException) {
            assertEquals("UNIQUE constraint failed", e.message)
            throw e
        }
    }

    @Test
    fun `getRecentSearchHistory should return empty list when no history exists`() = runTest {
        val emptyEntities = emptyList<SearchHistoryEntity>()

        coEvery { searchHistoryDao.getRecentSearchHistory() } returns flowOf(emptyEntities)

        repository.getRecentSearchHistory().test {
            val actual = awaitItem()
            assertTrue("Should return empty list", actual.isEmpty())
            awaitComplete()
        }

        coVerify(exactly = 1) { searchHistoryDao.getRecentSearchHistory() }
    }

    @Test
    fun `getRecentSearchHistory should handle database read error`() = runTest {
        val dbError = SQLException("Database connection failed")

        coEvery { searchHistoryDao.getRecentSearchHistory() } returns flow {
            throw dbError
        }

        repository.getRecentSearchHistory().test {
            val thrown = awaitError()
            assertTrue("Should be SQLException", thrown is SQLException)
            assertEquals("Database connection failed", thrown.message)
        }

        coVerify(exactly = 1) { searchHistoryDao.getRecentSearchHistory() }
    }

    @Test
    fun `saveSearchHistory should handle empty query string`() = runTest {
        val emptyQuery = ""

        coEvery { searchHistoryDao.insertSearchHistory(any()) } returns Unit

        repository.saveSearchHistory(emptyQuery)

        coVerify(exactly = 1) {
            searchHistoryDao.insertSearchHistory(
                match { entity -> entity.query.isEmpty() }
            )
        }
    }
}


