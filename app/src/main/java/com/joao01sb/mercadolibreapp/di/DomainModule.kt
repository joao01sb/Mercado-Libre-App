package com.joao01sb.mercadolibreapp.di

import com.joao01sb.mercadolibreapp.data.local.dao.SearchHistoryDao
import com.joao01sb.mercadolibreapp.domain.datasource.RemoteDataSource
import com.joao01sb.mercadolibreapp.data.repository.ProductRepositoryImpl
import com.joao01sb.mercadolibreapp.data.repository.SearchHistoryRepositoryImpl
import com.joao01sb.mercadolibreapp.domain.repository.ProductRepository
import com.joao01sb.mercadolibreapp.domain.repository.SearchHistoryRepository
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductCategoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDescriptionUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetProductDetailsUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.GetRecentSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.SaveSearchHistoryUseCase
import com.joao01sb.mercadolibreapp.domain.usecase.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideProductRepository(
        remoteDataSource: RemoteDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideSearchHistoryRepository(
        searchHistoryDao: SearchHistoryDao
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryDao)
    }

    @Provides
    fun provideSearchProductsUseCase(
        productRepository: ProductRepository
    ): SearchProductsUseCase {
        return SearchProductsUseCase(productRepository)
    }

    @Provides
    fun provideGetProductDetailsUseCase(
        productRepository: ProductRepository
    ): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(productRepository)
    }

    @Provides
    fun provideGetProductDescriptionUseCase(
        productRepository: ProductRepository
    ): GetProductDescriptionUseCase {
        return GetProductDescriptionUseCase(productRepository)
    }

    @Provides
    fun provideGetProductCategoryUseCase(
        productRepository: ProductRepository
    ): GetProductCategoryUseCase {
        return GetProductCategoryUseCase(productRepository)
    }

    @Provides
    fun provideSaveSearchHistoryUseCase(
        searchHistoryRepository: SearchHistoryRepository
    ): SaveSearchHistoryUseCase {
        return SaveSearchHistoryUseCase(searchHistoryRepository)
    }

    @Provides
    fun provideSearchHistoryUseCase(
        searchHistoryRepository: SearchHistoryRepository
    ): GetRecentSearchHistoryUseCase {
        return GetRecentSearchHistoryUseCase(searchHistoryRepository)
    }
}