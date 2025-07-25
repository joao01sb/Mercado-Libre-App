package com.joao01sb.mercadolibreapp.data.repository

import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import com.joao01sb.mercadolibreapp.data.local.entity.SearchHistoryEntity
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class SearchHistoryRepositoryImpl(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {

    override suspend fun saveSearchHistory(query: String) {
        searchHistoryDao.insertSearchHistory(SearchHistoryEntity(query = query))
    }

    override fun getRecentSearchHistory(): Flow<List<SearchHistory>> {
        return searchHistoryDao.getRecentSearchHistory()
            .map { entities -> entities.map { it.toSearchHistory() } }
    }

}
