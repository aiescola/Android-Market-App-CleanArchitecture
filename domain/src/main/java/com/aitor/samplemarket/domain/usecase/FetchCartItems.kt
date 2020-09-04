package com.aitor.samplemarket.domain.usecase

import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.repository.CartRepository
import javax.inject.Inject

class FetchCartItems @Inject constructor(private val cartRepository: CartRepository) {
    operator fun invoke(): List<CartItem> {
        return cartRepository.cartItems
    }
}