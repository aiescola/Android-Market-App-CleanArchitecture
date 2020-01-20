package com.aitor.samplemarket.repository

import com.aitor.samplemarket.BaseTest
import com.aitor.samplemarket.ProductMother
import com.aitor.samplemarket.cart.model.CartItem
import com.aitor.samplemarket.cart.repository.CartRepositoryImpl
import com.aitor.samplemarket.product.model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CartRepositoryTest : BaseTest() {

    private lateinit var cartRepository: CartRepositoryImpl

    @Before
    fun setup() {
        cartRepository = CartRepositoryImpl()
    }

    @Test
    fun `add product to cart for the first time`() {
        val (expectedProduct, expectedAddAmount) = givenAProductToAdd()

        cartRepository.addItemToCart(expectedProduct, expectedAddAmount)
        val testCartItems = cartRepository.cartItems

        assertCartItemsAreTheSame(expectedProduct, expectedAddAmount, testCartItems)
    }

    @Test
    fun `add same product to cart overrides product with new amount`() {
        val (expectedProduct, initialAmount) = givenAProductToAdd()
        val expectedAmount = initialAmount + 1

        cartRepository.addItemToCart(expectedProduct, initialAmount)
        cartRepository.addItemToCart(expectedProduct, expectedAmount)
        val testCartItems = cartRepository.cartItems

        assertCartItemsAreTheSame(expectedProduct, expectedAmount, testCartItems)
    }

    @Test
    fun `add multiple products to cart`() {
        val (expectedProducts, expectedAmount) = givenSomeProductsToAdd()

        expectedProducts.forEach {
            cartRepository.addItemToCart(it, expectedAmount)
        }

        val testCartItems = cartRepository.cartItems
        val testProducts = testCartItems.map { it.product }

        assertEquals(expectedProducts.size, testProducts.size)
    }

    @Test
    fun `delete item from cart`() {
        val (baseProducts, expectedAmount) = givenSomeProductsToAdd()
        baseProducts.forEach {
            cartRepository.addItemToCart(it, expectedAmount)
        }

        val productToDelete = baseProducts.first()

        cartRepository.deleteItemFromCart(productToDelete)

        val testProducts = cartRepository.cartItems.map { it.product }
        val itemFound = testProducts.contains(productToDelete)
        assertEquals(false, itemFound)
    }

    @Test
    fun `update product amount`() {
        val (expectedProduct, initialAmount) = givenAProductToAdd()
        val updateAmount = initialAmount + 1

        cartRepository.addItemToCart(expectedProduct, initialAmount)
        cartRepository.updateItem(expectedProduct, updateAmount)

        val testCartItems = cartRepository.cartItems

        assertCartItemsAreTheSame(expectedProduct, updateAmount, testCartItems)
    }

    private fun assertCartItemsAreTheSame(
        expectedProduct: Product, expectedAmount: Int, cartItems: List<CartItem>
    ) {
        assertEquals(cartItems.first().product, expectedProduct)
        assertEquals(cartItems.first().amount, expectedAmount)
    }

    private fun givenAProductToAdd(addAmount: Int = 2): Pair<Product, Int> {
        return ProductMother.listOfProducts.random() to addAmount
    }

    private fun givenSomeProductsToAdd(
        itemsToRetrieve: Int = 2, addAmount: Int = 2
    ): Pair<List<Product>, Int> {
        return ProductMother.listOfProducts.shuffled().take(itemsToRetrieve) to addAmount
    }

}