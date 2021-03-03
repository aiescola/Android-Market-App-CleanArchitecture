package com.aitor.samplemarket.base.di

import com.aitor.samplemarket.BuildConfig
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.repository.*
import com.aitor.samplemarket.domain.repository.CartRepository
import com.aitor.samplemarket.domain.repository.DiscountRepository
import com.aitor.samplemarket.domain.repository.ProductRepository
import com.aitor.samplemarket.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun providesApiClient(): ApiClient {
        return ApiClient(BuildConfig.BASE_URL, BuildConfig.DEBUG)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsDiscountRepository(
        discountRepositoryImpl: DiscountRepositoryImpl
    ): DiscountRepository

    @Binds
    @Singleton
    abstract fun bindsCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

}

