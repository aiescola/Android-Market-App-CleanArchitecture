package com.aitor.samplemarket.repository

import arrow.core.right
import com.aitor.samplemarket.NetworkProductMother
import com.aitor.samplemarket.data.network.datasource.NetworkProductDataSource
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import com.aitor.samplemarket.data.repository.ProductRepositoryImpl
import com.aitor.samplemarket.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {

    private val mockNetworkProductDataSource = mockk<NetworkProductDataSource>()
    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        productRepository =
            ProductRepositoryImpl(
                mockNetworkProductDataSource
            )
    }

    @Test
    fun `get products`() {
        val expectedProducts = givenANetworkProductAnswer()
        val testProducts = productRepository.fetchProducts()

        assertEquals(expectedProducts.asDomainModel().right(), testProducts)
    }

    private fun givenANetworkProductAnswer(): NetworkProductsAnswer {
        val networkProductAnswer =
            NetworkProductsAnswer(
                NetworkProductMother.networkProducts
            )
        every { mockNetworkProductDataSource.all } returns networkProductAnswer.right()
        return networkProductAnswer
    }
}