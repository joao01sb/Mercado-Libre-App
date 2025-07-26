package com.joao01sb.mercadolibreapp.data.remote.utils

import android.content.Context
import java.io.FileNotFoundException
import java.io.IOException

class AssetFileReader(private val context: Context) {

    fun readFile(filePath: String): String {
        return try {
            context.assets.open(filePath).bufferedReader().use { reader ->
                val content = reader.readText()
                if (content.isBlank()) {
                    throw IOException("Asset file is empty: $filePath")
                }
                content
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("Asset file not found: $filePath")
        } catch (e: IOException) {
            throw IOException("Failed to read asset file: $filePath", e)
        }
    }

    fun buildSearchFileName(query: String): String {
        val sanitizedQuery = query.lowercase().trim()
        return "searches/search-MLA-$sanitizedQuery.json"
    }
}
