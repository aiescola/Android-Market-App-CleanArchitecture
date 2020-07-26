package com.aitor.samplemarket.data.repository

import arrow.core.Option
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.data.network.datasource.TypeNetworkDiscountDataSource
import com.aitor.samplemarket.domain.repository.DiscountRepository

class DiscountRepositoryImpl(private val networkDiscountDataSource: com.aitor.samplemarket.data.network.datasource.TypeNetworkDiscountDataSource) :
    DiscountRepository {

    override fun fetchDiscounts(): Option<Discount> {
        return networkDiscountDataSource.all.map { it.asDomainModel() }
    }
}