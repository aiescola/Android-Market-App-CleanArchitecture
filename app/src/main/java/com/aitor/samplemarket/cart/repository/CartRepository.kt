package com.aitor.samplemarket.cart.repository

import com.aitor.samplemarket.cart.model.CartItem
import com.aitor.samplemarket.product.model.Product

interface CartRepository {
    val cartItems: List<CartItem>

    fun addItemToCart(product: Product, amount: Int)

    fun updateItem(product: Product, amount: Int): Boolean

    fun deleteItemFromCart(product: Product): Boolean
}
