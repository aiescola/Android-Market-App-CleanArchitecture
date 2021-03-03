package com.aitor.samplemarket.data.network.model

import com.aitor.samplemarket.domain.model.Product

/**
 *
 * Models for a response following this pattern:
 *
 * "products": [
 * {
 *    "code": "VOUCHER",
 *    "name": "Voucher",
 *    "price": 5
 * }, ...
 * ]
 */
data class NetworkProductsAnswer(val products: List<NetworkProduct>) {
    fun asDomainModel(): List<Product> = products.map {
        Product(it.code, it.name, it.price)
    }
}

data class NetworkProduct(
    val code: String, val name: String, val price: Double
)
