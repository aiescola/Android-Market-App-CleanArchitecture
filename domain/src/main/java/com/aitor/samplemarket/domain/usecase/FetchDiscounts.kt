package com.aitor.samplemarket.domain.usecase

import arrow.core.Option
import com.aitor.samplemarket.domain.model.Discount

class FetchDiscounts(private val discountRepository: com.aitor.samplemarket.domain.repository.DiscountRepository) {
    operator fun invoke(): Option<Discount> {
        return discountRepository.fetchDiscounts()
    }
}