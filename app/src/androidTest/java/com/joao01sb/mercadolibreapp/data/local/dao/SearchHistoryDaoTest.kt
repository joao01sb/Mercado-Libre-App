package com.joao01sb.mercadolibreapp.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.joao01sb.mercadolibreapp.data.local.database.MercadoLibreDatabase
import com.joao01sb.mercadolibreapp.data.local.entity.SearchHistoryEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchHistoryDaoTest {

    private lateinit var database: MercadoLibreDatabase
    private lateinit var dao: SearchHistoryDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MercadoLibreDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.searchHistoryDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSearchHistory_shouldSaveSearchQuery() = runTest {
        val searchEntity = SearchHistoryEntity("iphone 13", 1640995200000L)

        dao.insertSearchHistory(searchEntity)

        dao.getRecentSearchHistory().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("iphone 13", result[0].query)
            assertEquals(1640995200000L, result[0].timestamp)
        }
    }

    @Test
    fun insertSearchHistory_shouldReplaceExistingQuery() = runTest {
        val oldSearch = SearchHistoryEntity("samsung", 1640995200000L)
        val newSearch = SearchHistoryEntity("samsung", 1640995300000L)

        dao.insertSearchHistory(oldSearch)
        dao.insertSearchHistory(newSearch)

        dao.getRecentSearchHistory().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("samsung", result[0].query)
            assertEquals(1640995300000L, result[0].timestamp)
        }
    }

    @Test
    fun getRecentSearchHistory_shouldReturnInDescendingOrderByTimestamp() = runTest {
        val search1 = SearchHistoryEntity("old search", 1640995100000L)
        val search2 = SearchHistoryEntity("recent search", 1640995300000L)
        val search3 = SearchHistoryEntity("newest search", 1640995400000L)

        dao.insertSearchHistory(search1)
        dao.insertSearchHistory(search2)
        dao.insertSearchHistory(search3)

        dao.getRecentSearchHistory().test {
            val result = awaitItem()
            assertEquals(3, result.size)
            assertEquals("newest search", result[0].query)
            assertEquals("recent search", result[1].query)
            assertEquals("old search", result[2].query)
        }
    }

    @Test
    fun getRecentSearchHistory_shouldReturnEmptyListWhenNoData() = runTest {
        dao.getRecentSearchHistory().test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun getRecentSearchHistory_shouldLimitToTenResults() = runTest {
        repeat(12) { index ->
            val search = SearchHistoryEntity("search $index", 1640995000000L + index)
            dao.insertSearchHistory(search)
        }

        dao.getRecentSearchHistory().test {
            val result = awaitItem()
            assertEquals(10, result.size)
            assertEquals("search 11", result[0].query)
            assertEquals("search 10", result[1].query)
            assertEquals("search 2", result[9].query)
        }
    }
}
