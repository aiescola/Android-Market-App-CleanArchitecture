package com.aitor.samplemarket.product.repository

import arrow.core.Either
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.product.model.Product
import com.aitor.samplemarket.product.network.TypeNetworkProductDataSource

class ProductRepositoryImpl(private val networkProductDataSource: TypeNetworkProductDataSource) :
    ProductRepository {

    override fun fetchProducts(): Either<ApiClient.ApiError, List<Product>> {
        val products = networkProductDataSource.all

        return products.bimap(leftOperation = { it }, rightOperation = { it.asDomainModel() })
    }
}