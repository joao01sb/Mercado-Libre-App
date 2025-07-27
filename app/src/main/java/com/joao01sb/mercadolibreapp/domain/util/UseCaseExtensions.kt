package com.joao01sb.mercadolibreapp.domain.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.toResultUseCase(
    errorMessage: String? = null,
    tag: String? = null
): Flow<ResultUseCase<T>> {
    return this.map<T, ResultUseCase<T>> { data ->
        tag?.let { Log.d(it, "toResultUseCase: Successfully mapped data") }
        ResultUseCase.Success(data)
    }.catch { exception ->
        val message = errorMessage ?: "An error occurred: ${exception.message}"
        tag?.let { Log.e(it, "toResultUseCase: Error - $message", exception) }
        emit(ResultUseCase.Error(message))
    }
}

inline fun <T> safeExecute(
    tag: String? = null,
    crossinline validation: () -> Unit,
    crossinline execution: () -> Flow<T>,
    errorMessage: String? = null
): Flow<ResultUseCase<T>> {
    tag?.let { Log.d(it, "safeExecute: Starting safe execution") }

    return try {
        tag?.let { Log.d(it, "safeExecute: Running validation") }
        validation()
        tag?.let { Log.d(it, "safeExecute: Validation passed, executing flow") }

        execution()
            .onStart {
                tag?.let { Log.d(it, "safeExecute: Flow execution started") }
            }
            .onEach { data ->
                tag?.let { Log.d(it, "safeExecute: Flow emitted data") }
            }
            .toResultUseCase(errorMessage, tag)
    } catch (e: IllegalArgumentException) {
        tag?.let { Log.w(it, "safeExecute: Validation failed - ${e.message}", e) }
        flowOf(ResultUseCase.Error(e.message ?: "Invalid parameters"))
    } catch (e: Exception) {
        val message = errorMessage ?: "An unexpected error occurred: ${e.message}"
        tag?.let { Log.e(it, "safeExecute: Unexpected error - $message", e) }
        flowOf(ResultUseCase.Error(message))
    }
}
