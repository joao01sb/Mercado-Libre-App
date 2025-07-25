package com.joao01sb.mercadolibreapp.domain.repository

import com.joao01sb.mercadolibreapp.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun saveSearchHistory(query: String)
    fun getRecentSearchHistory(): Flow<List<SearchHistory>>
}
