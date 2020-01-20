package com.aitor.samplemarket.discount.repository

import arrow.core.Option
import com.aitor.samplemarket.discount.model.Discount

interface DiscountRepository {
    fun fetchDiscounts(): Option<Discount>
}
