package com.aitor.samplemarket.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Discount

interface DiscountRepository {
    fun fetchDiscounts(): Either<ApiError, Discount>
}
