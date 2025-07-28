package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecentSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    companion object {
        private const val TAG = "GetRecentSearchHistoryUseCase"
    }

    operator fun invoke(): Flow<ResultUseCase<List<String>>> = flow {
        Log.d(TAG, "invoke: Starting to fetch recent search history")
        try {
            repository.getRecentSearchHistory().collect { historyList ->
                Log.d(TAG, "invoke: Received ${historyList.size} history items from repository")
                val queries = historyList.map { it.query }
                Log.d(TAG, "invoke: Successfully mapped ${queries.size} search queries")
                emit(ResultUseCase.Success(queries))
            }
        } catch (exception: Exception) {
            Log.e(TAG, "invoke: Error fetching search history", exception)
            emit(ResultUseCase.Error("An error occurred while fetching search history: ${exception.message}"))
        }
    }
}
