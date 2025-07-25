package com.joao01sb.mercadolibreapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
) {

    fun toSearchHistory() = SearchHistory(query, timestamp)

}
