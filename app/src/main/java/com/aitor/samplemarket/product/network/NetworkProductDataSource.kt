package com.aitor.samplemarket.product.network

import arrow.core.Either
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.base.DataSource

typealias TypeNetworkProductDataSource = DataSource<Either<ApiClient.ApiError, NetworkProductsAnswer>>

class NetworkProductDataSource(private val apiClient: ApiClient) : TypeNetworkProductDataSource {

    override val all: Either<ApiClient.ApiError, NetworkProductsAnswer>
        get() = apiClient.allProducts
}