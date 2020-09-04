package com.aitor.samplemarket.data.repository

import arrow.core.Either
import com.aitor.samplemarket.data.network.datasource.NetworkProductDataSource
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val networkProductDataSource: NetworkProductDataSource
) : ProductRepository {

    override fun fetchProducts(): Either<ApiError, List<Product>> {
        val products = networkProductDataSource.all

        return products.bimap(leftOperation = { it }, rightOperation = { it.asDomainModel() })
    }
}