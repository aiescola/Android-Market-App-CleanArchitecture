package com.aitor.samplemarket.domain.repository

import arrow.core.Option
import com.aitor.samplemarket.domain.model.Discount

interface DiscountRepository {
    fun fetchDiscounts(): Option<Discount>
}
