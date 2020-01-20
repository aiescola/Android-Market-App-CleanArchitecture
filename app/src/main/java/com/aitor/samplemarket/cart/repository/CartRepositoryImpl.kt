package com.aitor.samplemarket.cart.repository

import com.aitor.samplemarket.cart.model.CartItem
import com.aitor.samplemarket.product.model.Product

class CartRepositoryImpl : CartRepository {
    private val hmCartItems = hashMapOf<String, CartItem>()

    override val cartItems: List<CartItem>
        get() = hmCartItems.values.map { it.copy() }

    override fun addItemToCart(product: Product, amount: Int) {
        hmCartItems[product.code] = CartItem(product, amount)
    }

    override fun updateItem(product: Product, amount: Int): Boolean {
        if (hmCartItems[product.code] != null) {
            hmCartItems[product.code]!!.amount = amount
            return true
        }
        return false
    }

    override fun deleteItemFromCart(product: Product): Boolean {
        if (hmCartItems[product.code] != null) {
            hmCartItems.remove(product.code)
            return true
        }
        return false
    }
}