package com.aitor.samplemarket.domain.usecase

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.repository.DiscountRepository
import javax.inject.Inject

class FetchDiscounts @Inject constructor(private val discountRepository: DiscountRepository) {
    operator fun invoke(): Either<ApiError, Discount> {
        return discountRepository.fetchDiscounts()
    }
}