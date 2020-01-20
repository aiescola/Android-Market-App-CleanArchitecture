package com.aitor.samplemarket.product.model

data class Product(
    val code: String,
    var name: String,
    var price: Double,
    var discountedPrice: Double? = null,
    var hasDiscount: Boolean = false
)
