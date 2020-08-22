package com.aitor.samplemarket.domain.usecase

import com.aitor.samplemarket.domain.model.Product
import javax.inject.Inject

class UpdateCartItem @Inject constructor(private val cartRepository: com.aitor.samplemarket.domain.repository.CartRepository) {
    operator fun invoke(product: Product, amount: Int) {
        cartRepository.updateItem(product, amount)
    }
}