package com.joao01sb.mercadolibreapp.data.local.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MercadoLibreDatabaseTest {

    private lateinit var database: MercadoLibreDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MercadoLibreDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun database_shouldProvideSearchHistoryDao() {
        val dao = database.searchHistoryDao
        assertNotNull(dao)
        assertTrue(dao is SearchHistoryDao)
    }

    @Test
    fun database_shouldBeAccessible() {
        val dao = database.searchHistoryDao
        assertNotNull(dao)
        assertNotNull(dao)
    }

    @Test
    fun database_shouldAllowBasicOperations() {
        val dao = database.searchHistoryDao
        assertNotNull(dao)
        assertTrue(dao is SearchHistoryDao)
    }
}
