package com.aitor.samplemarket.data.network.datasource

import arrow.core.Either
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.DataSource

typealias TypeNetworkProductDataSource = DataSource<Either<ApiError, NetworkProductsAnswer>>

class NetworkProductDataSource(private val apiClient: ApiClient) :
    TypeNetworkProductDataSource {

    override val all: Either<ApiError, NetworkProductsAnswer>
        get() = apiClient.allProducts
}