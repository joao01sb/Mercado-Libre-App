package com.joao01sb.mercadolibreapp.di

import android.content.Context
import androidx.room.Room
import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import com.joao01sb.mercadolibreapp.data.local.database.MercadoLibreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideMercadoLibreDatabase(
        @ApplicationContext context: Context
    ): MercadoLibreDatabase {
        return Room.databaseBuilder(
            context,
            MercadoLibreDatabase::class.java,
            "mercadolibre.db"
        ).build()
    }

    @Provides
    fun provideSearchDao(
        database: MercadoLibreDatabase
    ): SearchHistoryDao = database.searchHistoryDao
}
