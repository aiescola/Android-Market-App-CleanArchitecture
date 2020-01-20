package com.aitor.samplemarket.discount.model

/**
 * Domain models for discounts. Use of inheritance provides cleaner logic in the use cases
 */
sealed class Discount(val name: String, val description: String, val codes: List<String>) {
    override fun equals(other: Any?): Boolean {
        return if (other !is Discount) {
            false
        } else {
            other.name == name && other.description == description && other.codes == codes
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + codes.hashCode()
        return result
    }

    class ProductDiscount(
        name: String, description: String, codes: List<String>, val price: Double
    ) : Discount(name, description, codes)

    class BulkDiscount(
        name: String, description: String, codes: List<String>, val pct: Int, val minAmount: Int
    ) : Discount(name, description, codes)

    class PromotionDiscount(
        name: String,
        description: String,
        codes: List<String>,
        val buyRequiredAmount: Int,
        val pay: Int
    ) : Discount(name, description, codes)
}