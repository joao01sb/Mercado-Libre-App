package com.joao01sb.mercadolibreapp.di

import android.content.Context
import com.joao01sb.mercadolibreapp.domain.datasource.RemoteDataSource
import com.joao01sb.mercadolibreapp.data.remote.datasource.RemoteDataSourceImpl
import com.joao01sb.mercadolibreapp.data.remote.service.ApiService
import com.joao01sb.mercadolibreapp.data.remote.ApiServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return ApiServiceImp(context)
    }

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
}