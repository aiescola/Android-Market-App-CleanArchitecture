package com.aitor.samplemarket.data.repository

import arrow.core.Either
import com.aitor.samplemarket.data.network.datasource.NetworkDiscountDataSource
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.repository.DiscountRepository
import javax.inject.Inject

class DiscountRepositoryImpl @Inject constructor(private val networkDiscountDataSource: NetworkDiscountDataSource) :
    DiscountRepository {

    override fun fetchDiscounts(): Either<ApiError, Discount> {
        return networkDiscountDataSource.all.map { it.asDomainModel() }
    }
}