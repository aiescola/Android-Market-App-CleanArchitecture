package com.aitor.samplemarket.pro

import com.aitor.samplemarket.base.features.BottomMenuFeature
import com.aitor.samplemarket.base.features.SearchFilterFeature
import com.aitor.samplemarket.data.repository.ProductPremiumRepositoryImpl
import com.aitor.samplemarket.data.repository.UserRepositoryImpl
import com.aitor.samplemarket.domain.repository.ProductRepositoryPremium
import com.aitor.samplemarket.domain.repository.UserRepository
import com.aitor.samplemarket.domain.usecase.FetchProducts
import com.aitor.samplemarket.domain.usecase.FetchProductsPremiumImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object FeaturesModule {

    @Provides
    fun providesSearchFilterFeature(): SearchFilterFeature {
        return SearchFilterFeature(
            enabled = true
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
@InstallIn(ActivityRetainedComponent::class)
abstract class ProductsModule {

    @Binds
    abstract fun bindsFetchProducts(
        fetchProductsPremiumImpl: FetchProductsPremiumImpl
    ): FetchProducts

    @Binds
    abstract fun bindsProductRepository(
        productPremiumRepositoryImpl: ProductPremiumRepositoryImpl
    ): ProductRepositoryPremium

    @Binds
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

}
