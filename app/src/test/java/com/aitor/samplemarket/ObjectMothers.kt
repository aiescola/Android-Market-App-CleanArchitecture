package com.aitor.samplemarket

import com.aitor.samplemarket.domain.model.DiscountType
import com.aitor.samplemarket.data.network.model.NetworkDiscount
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.data.network.model.NetworkProduct
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer

object DiscountMother {
    val PRODUCT_DISCOUNT =
        NetworkDiscount(
            "DISCOUNT",
            DiscountType.PRODUCT.value,
            "PRODUCT_DISCOUNT",
            "description",
            listOf("PRODUCT_TEST_ID"),
            99.99
        )
    val BULK_DISCOUNT =
        NetworkDiscount(
            "DISCOUNT",
            DiscountType.BULK.value,
            "PRODUCT_DISCOUNT",
            "description",
            listOf("PRODUCT_TEST_ID"),
            minAmount = 99,
            pct = 9
        )
    val PROMOTION_DISCOUNT =
        NetworkDiscount(
            "DISCOUNT",
            DiscountType.PROMOTION.value,
            "PRODUCT_DISCOUNT",
            "description",
            listOf("PRODUCT_TEST_ID"),
            buy = 3,
            pay = 2
        )
}

object ProductMother {
    val listOfProducts = listOf(
        Product("mock1", "aa", 4.0),
        Product("mock2", "aab", 4.0),
        Product("mock3", "bbb", 3.5),
        Product("mock4", "baa", 3.5),
        Product("mock5", "ccc", 5.0),
        Product("mock6", "caa", 5.0)
    )

    val PRODUCTS_UPDATE =
        NetworkProductsAnswer(
            listOf(
                NetworkProduct(
                    "PRODUCT_TEST_ID",
                    "mock1",
                    5.0
                )
            )
        )
}

object NetworkProductMother {
    val networkProducts = listOf(
        NetworkProduct(
            "test code",
            "test name",
            3.0
        ),
        NetworkProduct(
            "test code 2",
            "test name 2",
            4.0
        )
    )
}