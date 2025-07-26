package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import javax.inject.Inject

class SaveSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(query: String): ResultUseCase<Unit> {
        return try {
            if (query.isNotBlank()) {
                repository.saveSearchHistory(query)
                ResultUseCase.Success(Unit)
            } else {
                ResultUseCase.Error("Search query cannot be empty")
            }
        } catch (e: Exception) {
            ResultUseCase.Error("Failed to save search history: ${e.message}")
        }
    }
}
