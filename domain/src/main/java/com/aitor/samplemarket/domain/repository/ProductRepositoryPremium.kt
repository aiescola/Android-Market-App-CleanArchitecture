package com.aitor.samplemarket.domain.repository

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product

interface ProductRepositoryPremium {

    fun fetchProducts(userId: String): Either<ApiError, List<Product>>

}