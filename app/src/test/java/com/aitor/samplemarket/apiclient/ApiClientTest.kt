package com.aitor.samplemarket.apiclient

import arrow.core.left
import arrow.core.right
import com.aitor.samplemarket.MockWebServerTest
import com.aitor.samplemarket.base.ApiClient
import com.aitor.samplemarket.product.network.NetworkProduct
import com.aitor.samplemarket.product.network.NetworkProductsAnswer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ApiClientTest : MockWebServerTest() {

    private lateinit var apiClient: ApiClient

    @Before
    override fun setUp() {
        super.setUp()
        val mockWebServerEndpoint = baseEndpoint
        apiClient = ApiClient(mockWebServerEndpoint)
    }

    @Test
    fun `request is sent and method is get`() {
        enqueueMockResponse(200, "network_products_response.json")

        apiClient.allProducts

        assertGetRequestSentTo("/bins/4bwec")
    }

    @Test
    fun `returns network products when status 200 and json okay`() {
        enqueueMockResponse(200, "network_products_response.json")

        val response = apiClient.allProducts

        assertEquals(expectedResponse().right(), response)
    }

    @Test
    fun `returns error when status 400`() {
        enqueueMockResponse(400)

        val response = apiClient.allProducts

        assertEquals(ApiClient.ApiError.UnknownError(400).left(), response)
    }

    @Test
    fun `returns error when status 404`() {
        enqueueMockResponse(404)

        val response = apiClient.allProducts

        assertEquals(ApiClient.ApiError.NotFoundError.left(), response)
    }

    @Test
    fun `returns empty list when body is empty`() {
        enqueueMockResponse(200, "network_products_empty.json")

        val response = apiClient.allProducts

        assertEquals(NetworkProductsAnswer(emptyList()).right(), response)
    }

    @Test
    fun `returns error when body is malformed`() {
        enqueueMockResponse(200, "network_products_wrong_response.json")

        val response = apiClient.allProducts

        assertEquals(ApiClient.ApiError.NetworkError.left(), response)
    }

    private fun expectedResponse(): NetworkProductsAnswer {
        return NetworkProductsAnswer(listOf(NetworkProduct("VOUCHER", "Cabify Voucher", 5.0),
            NetworkProduct("TSHIRT", "Cabify T-Shirt", 20.0),
            NetworkProduct("MUG", "Cabify Coffee Mug", 7.5)))
    }
}