package com.aitor.samplemarket.data.network.model

import com.aitor.samplemarket.domain.model.Product

data class NetworkProductsAnswerPremium(val products: List<NetworkProductPremium>) {
    fun asDomainModel(): List<Product> = products.map {
        Product(it.code, it.name, it.price, it.price - it.price * it.discountPct, true)
    }
}

data class NetworkProductPremium(
    val code: String, val name: String, val price: Double, val discountPct: Double
)
