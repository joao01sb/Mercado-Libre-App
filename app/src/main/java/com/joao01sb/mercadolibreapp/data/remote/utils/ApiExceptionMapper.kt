package com.joao01sb.mercadolibreapp.data.remote.utils

import kotlinx.serialization.SerializationException
import java.io.FileNotFoundException
import java.io.IOException

object ApiExceptionMapper {

    fun mapToApiException(exception: Exception, context: String): Exception {
        return when (exception) {
            is FileNotFoundException -> FileNotFoundException("$context - Mock data not available")
            is SerializationException -> SerializationException("$context - Invalid JSON format", exception)
            is IllegalArgumentException -> exception
            is IOException -> IOException(context, exception)
            else -> RuntimeException("$context - Unexpected error", exception)
        }
    }
}
