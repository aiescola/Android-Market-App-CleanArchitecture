package com.aitor.samplemarket.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.right
import arrow.core.some
import com.aitor.samplemarket.BaseTest
import com.aitor.samplemarket.DiscountMother.PRODUCT_DISCOUNT
import com.aitor.samplemarket.data.network.datasource.NetworkDiscountDataSource
import com.aitor.samplemarket.data.network.model.NetworkDiscount
import com.aitor.samplemarket.data.repository.DiscountRepositoryImpl
import com.aitor.samplemarket.domain.model.ApiError
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DiscountRepositoryTest : BaseTest() {

    private val fakeNetworkDiscountDataSource = mockk<NetworkDiscountDataSource>()
    private lateinit var discountRepository: DiscountRepositoryImpl

    @Before
    fun setup() {
        discountRepository =
            com.aitor.samplemarket.data.repository.DiscountRepositoryImpl(
                fakeNetworkDiscountDataSource
            )
    }

    @Test
    fun `gets a correct discount`() {
        val expectedDiscount = givenADiscount()
        val testDiscount = discountRepository.fetchDiscounts()

        assertEquals(expectedDiscount.map { it.asDomainModel() }, testDiscount)
    }

    private fun givenADiscount(): Either<ApiError, NetworkDiscount> {
        val discount = PRODUCT_DISCOUNT.right()
        every { fakeNetworkDiscountDataSource.all } returns discount
        return discount
    }
}