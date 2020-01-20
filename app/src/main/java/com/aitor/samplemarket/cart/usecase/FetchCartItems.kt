package com.aitor.samplemarket.cart.usecase

import com.aitor.samplemarket.cart.model.CartItem
import com.aitor.samplemarket.cart.repository.CartRepository

class FetchCartItems(private val cartRepository: CartRepository) {
    operator fun invoke(): List<CartItem> {
        return cartRepository.cartItems
    }
}