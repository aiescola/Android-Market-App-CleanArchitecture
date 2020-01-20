package com.aitor.samplemarket.cart.usecase

import com.aitor.samplemarket.cart.repository.CartRepository
import com.aitor.samplemarket.product.model.Product

class UpdateCartItem(private val cartRepository: CartRepository) {
    operator fun invoke(product: Product, amount: Int) {
        cartRepository.updateItem(product, amount)
    }
}