package com.aitor.samplemarket.base.di

import com.aitor.samplemarket.BuildConfig
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { com.aitor.samplemarket.data.network.ApiClient(BuildConfig.BASE_URL) }
}

val productModule = module {
    single<ProductRepository> {
        com.aitor.samplemarket.data.repository.ProductRepositoryImpl(
            com.aitor.samplemarket.data.network.datasource.NetworkProductDataSource(
                get()
            )
        )
    }
    factory { FetchProducts(get()) }
}

val discountModule = module {
    single<com.aitor.samplemarket.domain.repository.DiscountRepository> {
        com.aitor.samplemarket.data.repository.DiscountRepositoryImpl(
            com.aitor.samplemarket.data.network.datasource.FakeNetworkDiscountDataSource()
        )
    }
    factory { FetchDiscounts(get()) }
}

val cartModule = module {
    single<com.aitor.samplemarket.domain.repository.CartRepository> { com.aitor.samplemarket.data.repository.CartRepositoryImpl() }
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
