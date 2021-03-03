package com.aitor.samplemarket.domain.repository

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product

interface ProductRepository {

    fun fetchProducts(): Either<ApiError, List<Product>>

}
