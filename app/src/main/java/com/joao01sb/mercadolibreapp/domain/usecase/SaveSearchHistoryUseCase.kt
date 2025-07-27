package com.joao01sb.mercadolibreapp.domain.usecase

import android.util.Log
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import javax.inject.Inject

class SaveSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    companion object {
        private const val TAG = "SaveSearchHistoryUseCase"
    }

    suspend operator fun invoke(query: String): ResultUseCase<Unit> {
        Log.d(TAG, "invoke: Attempting to save search query='$query'")

        return try {
            if (query.isNotBlank()) {
                Log.d(TAG, "invoke: Query is valid, saving to repository")
                repository.saveSearchHistory(query)
                Log.d(TAG, "invoke: Successfully saved search query")
                ResultUseCase.Success(Unit)
            } else {
                Log.w(TAG, "invoke: Query is blank, cannot save")
                ResultUseCase.Error("Search query cannot be empty")
            }
        } catch (e: Exception) {
            Log.e(TAG, "invoke: Failed to save search history", e)
            ResultUseCase.Error("Failed to save search history: ${e.message}")
        }
    }
}
