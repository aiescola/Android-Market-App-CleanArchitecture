package com.aitor.samplemarket.data.network.datasource

import arrow.core.Either
import arrow.core.right
import com.aitor.samplemarket.data.network.ApiClient
import com.aitor.samplemarket.data.network.model.NetworkProduct
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import com.aitor.samplemarket.domain.model.ApiError
import javax.inject.Inject


class NetworkProductDataSource @Inject constructor(private val apiClient: ApiClient) {

    //val all: Either<ApiError, NetworkProductsAnswer> get() = apiClient.allProducts
    val all: Either<ApiError, NetworkProductsAnswer>
        get() = NetworkProductsAnswer(
            listOf(
                NetworkProduct(
                    "test code",
                    "test name",
                    3.0
                ),
                NetworkProduct(
                    "test code 2",
                    "test name 2",
                    4.0
                )
            )
        ).right()

}
