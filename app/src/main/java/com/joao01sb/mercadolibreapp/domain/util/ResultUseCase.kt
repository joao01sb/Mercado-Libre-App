package com.joao01sb.mercadolibreapp.domain.util

sealed class ResultUseCase<out T> {
    data class Success<T>(val data: T) : ResultUseCase<T>()
    data class Error(val message: String) : ResultUseCase<Nothing>()
}
