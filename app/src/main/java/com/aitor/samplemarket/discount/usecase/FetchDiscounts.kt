package com.aitor.samplemarket.discount.usecase

import arrow.core.Option
import com.aitor.samplemarket.discount.model.Discount
import com.aitor.samplemarket.discount.repository.DiscountRepository

class FetchDiscounts(private val discountRepository: DiscountRepository) {
    operator fun invoke(): Option<Discount> {
        return discountRepository.fetchDiscounts()
    }
}