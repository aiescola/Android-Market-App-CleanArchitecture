package com.aitor.samplemarket.domain.repository

import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.model.Product

interface CartRepository {
    val cartItems: List<CartItem>

    fun addItemToCart(product: Product, amount: Int)

    fun updateItem(product: Product, amount: Int): Boolean

    fun deleteItemFromCart(product: Product): Boolean
}
