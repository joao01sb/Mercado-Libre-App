package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetRecentSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    companion object {
        private const val TAG = "GetRecentSearchHistoryUseCase"
    }

    operator fun invoke(): Flow<ResultUseCase<List<String>>> {
        Log.d(TAG, "invoke: Starting to fetch recent search history")
        return repository.getRecentSearchHistory()
            .onStart {
                Log.d(TAG, "invoke: Repository flow started")
            }
            .map { historyList ->
                Log.d(TAG, "invoke: Received ${historyList.size} history items from repository")
                val queries = historyList.map { it.query }
                Log.d(TAG, "invoke: Successfully mapped ${queries.size} search queries")
                ResultUseCase.Success(queries)
            }
            .catch { exception ->
                Log.e(TAG, "invoke: Error fetching search history", exception)
                ResultUseCase.Error("An error occurred while fetching search history: ${exception.message}")
            }
    }
}
