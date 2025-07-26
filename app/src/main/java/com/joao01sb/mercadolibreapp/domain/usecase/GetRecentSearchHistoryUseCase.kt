package com.joao01sb.mercadolibreapp.domain.usecase

import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.util.ResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    operator fun invoke(): Flow<ResultUseCase<List<String>>> {
        return repository.getRecentSearchHistory().map { historyList ->
            val queries = historyList.map { it.query }
            ResultUseCase.Success(queries)
        }.catch {
            ResultUseCase.Error("An error occurred while fetching search history: ${it.message}")
        }
    }
}
