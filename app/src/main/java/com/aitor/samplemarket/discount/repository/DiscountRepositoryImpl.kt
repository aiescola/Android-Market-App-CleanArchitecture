package com.aitor.samplemarket.discount.repository

import arrow.core.Option
import com.aitor.samplemarket.discount.model.Discount
import com.aitor.samplemarket.discount.network.TypeNetworkDiscountDataSource

class DiscountRepositoryImpl(private val networkDiscountDataSource: TypeNetworkDiscountDataSource) :
    DiscountRepository {

    override fun fetchDiscounts(): Option<Discount> {
        return networkDiscountDataSource.all.map { it.asDomainModel() }
    }
}