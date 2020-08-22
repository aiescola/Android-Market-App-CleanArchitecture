package com.aitor.samplemarket.base.di

import com.aitor.samplemarket.BuildConfig
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.network.datasource.FakeNetworkDiscountDataSource
import com.aitor.samplemarket.data.network.datasource.NetworkProductDataSource
import com.aitor.samplemarket.data.network.datasource.TypeNetworkDiscountDataSource
import com.aitor.samplemarket.data.network.datasource.TypeNetworkProductDataSource
import com.aitor.samplemarket.data.repository.CartRepositoryImpl
import com.aitor.samplemarket.data.repository.DiscountRepositoryImpl
import com.aitor.samplemarket.data.repository.ProductRepositoryImpl
import com.aitor.samplemarket.domain.repository.CartRepository
import com.aitor.samplemarket.domain.repository.DiscountRepository
import com.aitor.samplemarket.domain.usecase.AddItemToCart
import com.aitor.samplemarket.domain.usecase.DeleteCartItem
import com.aitor.samplemarket.domain.usecase.FetchCartItems
import com.aitor.samplemarket.domain.usecase.UpdateCartItem
import com.aitor.samplemarket.domain.usecase.FetchDiscounts
import com.aitor.samplemarket.domain.repository.ProductRepository
import com.aitor.samplemarket.domain.usecase.FetchProducts
import com.aitor.samplemarket.shop.screens.cart.CartViewModel
import com.aitor.samplemarket.shop.screens.shop.ShopViewModel
import com.aitor.samplemarket.splash.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
val apiModule = module {
    single { ApiClient(BuildConfig.BASE_URL) }
}

val productModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(
            NetworkProductDataSource(
                get()
            )
        )
    }
    factory { FetchProducts(get()) }
}

val discountModule = module {
    single<DiscountRepository> {
        DiscountRepositoryImpl(
            FakeNetworkDiscountDataSource()
        )
    }
    factory { FetchDiscounts(get()) }
}

val cartModule = module {
    single<CartRepository> { CartRepositoryImpl() }
    factory { AddItemToCart(get()) }
    factory { FetchCartItems(get()) }
    factory { UpdateCartItem(get()) }
    factory { DeleteCartItem(get()) }
}

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { ShopViewModel(get(), get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get()) }
}
*/

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
object DataSourceModule {

    @Provides
    fun provideNetworkProductDataSource(apiClient: ApiClient): TypeNetworkProductDataSource {
        return NetworkProductDataSource(apiClient)
    }

    @Provides
    fun provideNetworkDiscountDataSource(): TypeNetworkDiscountDataSource {
        return FakeNetworkDiscountDataSource()
    }
}

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideProductRepository(networkProductDataSource: TypeNetworkProductDataSource): ProductRepository {
        return ProductRepositoryImpl(networkProductDataSource)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDiscountRepository(networkDiscountDataSource: TypeNetworkDiscountDataSource): DiscountRepository {
        return DiscountRepositoryImpl(networkDiscountDataSource)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl()
    }
}