package com.aitor.samplemarket.domain.usecase

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepositoryPremium
import com.aitor.samplemarket.domain.repository.UserRepository
import javax.inject.Inject

class FetchProductsPremiumImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepositoryPremium: ProductRepositoryPremium
) : FetchProducts {
    override operator fun invoke(filterCriteria: String?): Either<ApiError, List<Product>> {
        val userId = userRepository.getUser().userId
        val result = productRepositoryPremium.fetchProducts(userId)
        return filterCriteria?.let { criteria ->
            result.map { products ->
                products.filter { it.name.contains(criteria, ignoreCase = true) }
            }
        } ?: result
    }
}
