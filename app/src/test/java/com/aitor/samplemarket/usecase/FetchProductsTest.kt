package com.aitor.samplemarket.usecase

import arrow.core.right
import com.aitor.samplemarket.ProductObjectMother.listOfProducts
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.ProductRepository
import com.aitor.samplemarket.domain.usecase.FetchProducts
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchProductsTest {
    private val productRepository = mockk<ProductRepository>()
    private lateinit var fetchProducts: FetchProducts

    @Before
    fun setup() {
        fetchProducts =
            FetchProducts(productRepository)
    }

    @Test
    fun `returns all products when no filters are passed`() {
        val expectedProducts = givenAListOfProducts()

        val testProducts = fetchProducts()

        assertEquals(expectedProducts.right(), testProducts)
    }

    @Test
    fun `returns filtered products when a filter is passed`() {
        val criteria = "aa"
        val expectedProducts =
            givenAListOfProducts().filter { it.name.contains(criteria, ignoreCase = true) }

        val testProducts = fetchProducts(criteria)

        assertEquals(expectedProducts.right(), testProducts)
    }

    private fun givenAListOfProducts(): List<Product> {
        every { productRepository.fetchProducts() } returns listOfProducts.right()
        return listOfProducts
    }
}