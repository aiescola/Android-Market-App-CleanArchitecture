package com.aitor.samplemarket.domain.model

data class CartItem(val product: Product, var amount: Int, var discountedSubtotal: Double? = null) {
    val nonDiscountedSubtotal
        get() = product.price * amount
}
