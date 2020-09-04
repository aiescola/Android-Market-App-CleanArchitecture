package com.aitor.samplemarket.data.network.datasource

import arrow.core.Either
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import com.aitor.samplemarket.domain.model.ApiError
import javax.inject.Inject

class NetworkProductDataSource @Inject constructor(private val apiClient: ApiClient) {

    val all: Either<ApiError, NetworkProductsAnswer> get() = apiClient.allProducts
}