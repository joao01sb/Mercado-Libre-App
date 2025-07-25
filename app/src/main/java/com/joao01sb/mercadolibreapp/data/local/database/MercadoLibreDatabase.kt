package com.joao01sb.mercadolibreapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import com.joao01sb.mercadolibreapp.data.local.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1
)
abstract class MercadoLibreDatabase : RoomDatabase() {
    abstract val searchHistoryDao: SearchHistoryDao
}
