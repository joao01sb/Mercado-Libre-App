package com.joao01sb.mercadolibreapp.data.mock

import com.joao01sb.mercadolibreapp.data.local.entity.SearchHistoryEntity
import com.joao01sb.mercadolibreapp.domain.model.SearchHistory

object MockSearchHistory {
    val mockSearchHistoryEntities = listOf(
        SearchHistoryEntity(query = "iphone 13", timestamp = 1672531200000L),
        SearchHistoryEntity(query = "samsung galaxy", timestamp = 1672617600000L),
        SearchHistoryEntity(query = "macbook pro", timestamp = 1672704000000L)
    )

    val mockSearchHistoryDomain = listOf(
        SearchHistory(query = "iphone 13", timestamp = 1672531200000L),
        SearchHistory(query = "samsung galaxy", timestamp = 1672617600000L),
        SearchHistory(query = "macbook pro", timestamp = 1672704000000L)
    )

    val mockSearchHistoryToString = mockSearchHistoryDomain.map { it.query }

    val singleSearchHistoryEntity = listOf(
        SearchHistoryEntity(query = "única busca", timestamp = System.currentTimeMillis())
    )

    val singleSearchHistoryDomain = listOf(
        SearchHistory(query = "única busca", timestamp = System.currentTimeMillis())
    )
}