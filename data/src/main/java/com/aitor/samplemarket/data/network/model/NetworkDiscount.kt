package com.aitor.samplemarket.data.network.model

import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.model.DiscountType

/**
 * The backend could return three types of discounts.
 *
 * 1º A PRICE discount for particular products:
 * {
 *    "type": "product"
 *    "name": "Black Friday!",
 *    "description" : "discount description",
 *    "productCodes": ["TSHIRT", "VOUCHER"],
 *    "price": "9999.99"
 * }
 *
 * 2º a BULK discount that applies a discount percentage if a minimum amount of products is
 * satisfied:
 * {
 *    "type": "bulk",
 *    "name": "10% off in MUGS!",
 *    "description" : "discount description",
 *    "productCodes": ["MUG"],
 *    "pct": "10",
 *    "minAmount": "100"
 * }
 *
 * 3º A promotion like 2x1 or 3x2, being BUY the needed items to buy and PAY the
 * amount of items paid:
 * {
 *    "type": "promo",
 *    "name": "3x2 in ASDF!",
 *    "description" : "discount description",
 *    "productCodes": ["ASDF"],
 *    "buy": "3"
 *    "pay": "2",
 * }
 */

data class NetworkDiscount(
    val code: String,
    val type: String,
    val name: String,
    val description: String,
    val productCodes: List<String>,
    // For products
    val price: Double? = null,
    // For bulk
    val pct: Int? = null,
    val minAmount: Int? = null,
    // For promotions
    val buy: Int? = null,
    val pay: Int? = null
) {
    fun asDomainModel(): Discount {
        return when (type) {
            DiscountType.BULK.value -> Discount.BulkDiscount(
                name, description, productCodes, pct = pct!!, minAmount = minAmount!!
            )
            DiscountType.PRODUCT.value -> Discount.ProductDiscount(
                name, description, productCodes, price = price!!
            )
            else -> Discount.PromotionDiscount(
                name, description, productCodes, buy!!, pay!!
            )
        }
    }
}
