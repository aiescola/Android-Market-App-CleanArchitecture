package com.aitor.samplemarket.data.repository

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepository
import com.aitor.samplemarket.data.network.datasource.TypeNetworkProductDataSource

class ProductRepositoryImpl(private val networkProductDataSource: TypeNetworkProductDataSource) :
    ProductRepository {

    override fun fetchProducts(): Either<ApiError, List<Product>> {
        val products = networkProductDataSource.all

        return products.bimap(leftOperation = { it }, rightOperation = { it.asDomainModel() })
    }
}