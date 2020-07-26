package com.aitor.samplemarket.domain.usecase

import com.aitor.samplemarket.domain.model.Product

class AddItemToCart(private val cartRepository: com.aitor.samplemarket.domain.repository.CartRepository) {

    operator fun invoke(product: Product, amount: Int) {
        cartRepository.addItemToCart(product, amount)
    }
}