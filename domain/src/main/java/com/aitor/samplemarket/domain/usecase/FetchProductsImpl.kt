package com.aitor.samplemarket.domain.usecase

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepository
import javax.inject.Inject

class FetchProductsImpl @Inject constructor(private val productRepository: ProductRepository) :
    FetchProducts {
    override operator fun invoke(filterCriteria: String?): Either<ApiError, List<Product>> {
        val result = productRepository.fetchProducts()
        return filterCriteria?.let { criteria ->
            result.map { products ->
                products.filter { it.name.contains(criteria, ignoreCase = true) }
            }
        } ?: result
    }
}
