package com.aitor.samplemarket.data.network.datasource

import arrow.core.Either
import arrow.core.right
import com.aitor.samplemarket.data.network.model.NetworkProductPremium
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswerPremium
import com.aitor.samplemarket.domain.model.ApiError
import javax.inject.Inject

class NetworkProductPremiumDataSource @Inject constructor() {

    val all: Either<ApiError, NetworkProductsAnswerPremium>
        get() = NetworkProductsAnswerPremium(
            listOf(
                NetworkProductPremium(
                    "test code",
                    "test name",
                    3.0,
                    0.1,
                ),
                NetworkProductPremium(
                    "test code 2",
                    "test name 2",
                    4.0,
                    0.15,
                )
            )
        ).right()

}