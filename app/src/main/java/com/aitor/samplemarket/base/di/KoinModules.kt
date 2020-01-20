package com.aitor.samplemarket.base.di

import com.aitor.samplemarket.BuildConfig
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.cart.repository.CartRepository
import com.aitor.samplemarket.cart.repository.CartRepositoryImpl
import com.aitor.samplemarket.cart.usecase.AddItemToCart
import com.aitor.samplemarket.cart.usecase.DeleteCartItem
import com.aitor.samplemarket.cart.usecase.FetchCartItems
import com.aitor.samplemarket.cart.usecase.UpdateCartItem
import com.aitor.samplemarket.discount.network.FakeNetworkDiscountDataSource
import com.aitor.samplemarket.discount.repository.DiscountRepository
import com.aitor.samplemarket.discount.repository.DiscountRepositoryImpl
import com.aitor.samplemarket.discount.usecase.FetchDiscounts
import com.aitor.samplemarket.product.network.NetworkProductDataSource
import com.aitor.samplemarket.product.repository.ProductRepository
import com.aitor.samplemarket.product.repository.ProductRepositoryImpl
import com.aitor.samplemarket.product.usecase.FetchProducts
import com.aitor.samplemarket.shop.screens.cart.CartViewModel
import com.aitor.samplemarket.shop.screens.shop.ShopViewModel
import com.aitor.samplemarket.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiClient(BuildConfig.BASE_URL) }
}

val productModule = module {
    single<ProductRepository> { ProductRepositoryImpl(NetworkProductDataSource(get())) }
    factory { FetchProducts(get()) }
}

val discountModule = module {
    single<DiscountRepository> { DiscountRepositoryImpl(FakeNetworkDiscountDataSource()) }
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
