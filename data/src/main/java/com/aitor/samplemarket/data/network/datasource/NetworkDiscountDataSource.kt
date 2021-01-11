package com.aitor.samplemarket.data.network.datasource

import arrow.core.Either
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.network.model.NetworkDiscount
import com.aitor.samplemarket.domain.model.ApiError
import javax.inject.Inject

class NetworkDiscountDataSource @Inject constructor(private val apiClient: ApiClient) {
    val all: Either<ApiError, NetworkDiscount> get() = apiClient.discounts
}