package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.sql.SQLException

class SaveSearchHistoryUseCaseTest {

    private lateinit var repository: SearchHistoryRepository
    private lateinit var useCase: SaveSearchHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = SaveSearchHistoryUseCase(repository)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should return success when saving valid query`() = runTest {
        val query = "iphone 13"

        coEvery { repository.saveSearchHistory(query) } returns Unit

        val result = useCase.invoke(query)

        assertTrue("Should be Success", result is ResultUseCase.Success)
        assertEquals(Unit, (result as ResultUseCase.Success).data)

        coVerify(exactly = 1) { repository.saveSearchHistory(query) }

        verify { Log.d("SaveSearchHistoryUseCase", "invoke: Starting to save search query: $query") }
        verify { Log.d("SaveSearchHistoryUseCase", "invoke: Successfully saved search query: $query") }
    }

    @Test
    fun `invoke should return error when query is blank`() = runTest {
        val blankQuery = ""

        val result = useCase.invoke(blankQuery)

        assertTrue("Should be Error", result is ResultUseCase.Error)
        assertEquals("Search query cannot be empty", (result as ResultUseCase.Error).message)

        coVerify(exactly = 0) { repository.saveSearchHistory(any()) }

        verify { Log.d("SaveSearchHistoryUseCase", "invoke: Starting to save search query: $blankQuery") }
        verify { Log.w("SaveSearchHistoryUseCase", "invoke: Query validation failed - empty or blank query") }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {
        val query = "samsung galaxy"
        val exception = SQLException("Database connection failed")

        coEvery { repository.saveSearchHistory(query) } throws exception

        val result = useCase.invoke(query)

        assertTrue("Should be Error", result is ResultUseCase.Error)
        assertEquals("Failed to save search history: Database connection failed", (result as ResultUseCase.Error).message)

        coVerify(exactly = 1) { repository.saveSearchHistory(query) }

        verify { Log.d("SaveSearchHistoryUseCase", "invoke: Starting to save search query: $query") }
        verify { Log.e("SaveSearchHistoryUseCase", "invoke: Error saving search history for query: $query", exception) }
    }
}
