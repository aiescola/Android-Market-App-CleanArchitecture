package com.aitor.samplemarket.cart.model

import com.aitor.samplemarket.product.model.Product

data class CartItem(val product: Product, var amount: Int, var discountedSubtotal: Double? = null) {
    val nonDiscountedSubtotal
        get() = product.price * amount
}
