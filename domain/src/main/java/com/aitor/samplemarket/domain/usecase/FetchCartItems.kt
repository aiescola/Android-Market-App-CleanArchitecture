package com.aitor.samplemarket.domain.usecase

import com.aitor.samplemarket.domain.model.CartItem

class FetchCartItems(private val cartRepository: com.aitor.samplemarket.domain.repository.CartRepository) {
    operator fun invoke(): List<CartItem> {
        return cartRepository.cartItems
    }
}