package com.aitor.samplemarket.base.di

import com.aitor.samplemarket.BuildConfig
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.repository.CartRepositoryImpl
import com.aitor.samplemarket.data.repository.DiscountRepositoryImpl
import com.aitor.samplemarket.data.repository.ProductRepositoryImpl
import com.aitor.samplemarket.domain.repository.CartRepository
import com.aitor.samplemarket.domain.repository.DiscountRepository
import com.aitor.samplemarket.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)

object ApiModule {
    @Provides
    fun proviceApiClient(): ApiClient {
        return ApiClient(BuildConfig.BASE_URL)
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindsDiscountRepository(
        discountRepositoryImpl: DiscountRepositoryImpl
    ): DiscountRepository

    @Binds
    abstract fun bindsCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}