package com.aitor.samplemarket.data.repository

import arrow.core.Either
import com.aitor.samplemarket.data.network.datasource.NetworkProductPremiumDataSource
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepositoryPremium
import javax.inject.Inject

class ProductPremiumRepositoryImpl @Inject constructor(private val networkProductDataSource: NetworkProductPremiumDataSource) :
    ProductRepositoryPremium {

    override fun fetchProducts(userId: String): Either<ApiError, List<Product>> {
        val products = networkProductDataSource.all

        return products.bimap(leftOperation = { it }, rightOperation = { it.asDomainModel() })
    }

}