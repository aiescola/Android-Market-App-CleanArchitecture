package com.aitor.samplemarket.product.repository

import arrow.core.Either
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.product.model.Product

interface ProductRepository {

    fun fetchProducts(): Either<ApiClient.ApiError, List<Product>>
}
