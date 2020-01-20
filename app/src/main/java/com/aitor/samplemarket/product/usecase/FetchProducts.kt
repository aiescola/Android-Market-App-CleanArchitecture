package com.aitor.samplemarket.product.usecase

import arrow.core.Either
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.product.model.Product
import com.aitor.samplemarket.product.repository.ProductRepository

class FetchProducts(private val productRepository: ProductRepository) {
    operator fun invoke(filterCriteria: String? = null): Either<ApiClient.ApiError, List<Product>> {
        val result = productRepository.fetchProducts()
        return filterCriteria?.let { criteria ->
            result.map { products ->
                products.filter { it.name.contains(criteria, ignoreCase = true) }
            }
        } ?: result
    }
}