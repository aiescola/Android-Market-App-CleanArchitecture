package com.aitor.samplemarket.data.repository

import arrow.core.Option
import com.aitor.samplemarket.data.network.datasource.FakeNetworkDiscountDataSource
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.repository.DiscountRepository
import javax.inject.Inject

class DiscountRepositoryImpl @Inject constructor(
    private val networkDiscountDataSource: FakeNetworkDiscountDataSource
) : DiscountRepository {

    override fun fetchDiscounts(): Option<Discount> {
        return networkDiscountDataSource.all.map { it.asDomainModel() }
    }
}