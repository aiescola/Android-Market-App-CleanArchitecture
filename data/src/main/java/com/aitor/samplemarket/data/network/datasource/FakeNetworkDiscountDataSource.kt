package com.aitor.samplemarket.data.network.datasource

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.aitor.samplemarket.data.network.model.NetworkDiscount
import com.aitor.samplemarket.domain.model.DiscountType
import java.util.*
import javax.inject.Inject

class FakeNetworkDiscountDataSource @Inject constructor() {
    private val discount = if (Random().nextBoolean()) randomDiscount.some() else None

    val all: Option<NetworkDiscount> get() = discount
}

private val randomDiscount: NetworkDiscount
    get() = networkDiscounts.shuffled().first()

private val networkDiscounts = listOf(
    NetworkDiscount(
        "PRM01",
        DiscountType.PROMOTION.value,
        "one product promotion",
        "Buy 2 pay 1",
        listOf("TSHIRT"),
        buy = 2,
        pay = 1
    ), NetworkDiscount(
        "PRM02",
        DiscountType.PROMOTION.value,
        "two products promotion",
        "Buy three and pay two in our featured products!",
        listOf("MUG", "VOUCHER"),
        buy = 3,
        pay = 2
    ), NetworkDiscount(
        "BULK01",
        DiscountType.BULK.value,
        "One item bulk",
        "25% off in mugs buying more than 10!",
        listOf("MUG"),
        minAmount = 10,
        pct = 25
    ), NetworkDiscount(
        "BULK02",
        DiscountType.BULK.value,
        "Two items bulk",
        "10% off buying more than 10 of our selected products!",
        listOf("MUG", "TSHIRT"),
        minAmount = 10,
        pct = 10
    ), NetworkDiscount(
        "PRD01",
        DiscountType.PRODUCT.value,
        "One Product discount (voucher)",
        "Vouchers at 3.95 for a limited time",
        listOf("VOUCHER"),
        price = 3.95
    ), NetworkDiscount(
        "PRD02",
        DiscountType.PRODUCT.value,
        "One Product discount (t-shirt)",
        "Cheap tshirts for summer",
        listOf("TSHIRT"),
        price = 16.95
    )
)