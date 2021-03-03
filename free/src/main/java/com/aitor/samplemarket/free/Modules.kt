package com.aitor.samplemarket.free

import com.aitor.samplemarket.base.features.BottomMenuFeature
import com.aitor.samplemarket.base.features.SearchFilterFeature
import com.aitor.samplemarket.data.repository.ProductRepositoryImpl
import com.aitor.samplemarket.domain.repository.ProductRepository
import com.aitor.samplemarket.domain.usecase.FetchProducts
import com.aitor.samplemarket.domain.usecase.FetchProductsImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object FeaturesModule {

    @Provides
    fun providesSearchFilterFeature(): SearchFilterFeature {
        return SearchFilterFeature(
            enabled = false
        )
    }

    @Provides
    fun providesBottomMenuFeature(): BottomMenuFeature {
        return BottomMenuFeature(
            R.menu.bottom_nav_menu,
            R.navigation.navigation
        )
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsModule {

    @Binds
    @Singleton
    abstract fun bindsFetchProducts(
        fetchProductsImpl: FetchProductsImpl
    ): FetchProducts

    @Binds
    @Singleton
    abstract fun bindsProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

}
