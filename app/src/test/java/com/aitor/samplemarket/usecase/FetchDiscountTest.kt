package com.aitor.samplemarket.usecase

import arrow.core.right
import arrow.core.some
import com.aitor.samplemarket.DiscountMother.PRODUCT_DISCOUNT
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.repository.DiscountRepository
import com.aitor.samplemarket.domain.usecase.FetchDiscounts
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchDiscountTest {

    private val discountRepository = mockk<DiscountRepository>()
    private lateinit var fetchDiscounts: FetchDiscounts

    @Before
    fun setupTests() {
        fetchDiscounts = FetchDiscounts(
            discountRepository
        )
    }

    @Test
    fun `fetch discount `() {
        val expectedDiscount = givenOneDiscount()

        val testDiscount = fetchDiscounts()

        assertEquals(expectedDiscount.right(), testDiscount)
    }

    private fun givenOneDiscount(): Discount {
        val discount = PRODUCT_DISCOUNT.asDomainModel()

        every { discountRepository.fetchDiscounts() } returns discount.right()

        return discount
    }
}