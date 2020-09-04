package com.aitor.samplemarket.data.repository

import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor() : CartRepository {
    private val hmCartItems = hashMapOf<String, CartItem>()

    override val cartItems: List<CartItem>
        get() = hmCartItems.values.map { it.copy() }

    override fun addItemToCart(product: Product, amount: Int) {
        hmCartItems[product.code] =
            CartItem(product, amount)
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