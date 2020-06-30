package com.aitor.samplemarket.product.network

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @get:GET(value = "/api/products")
    val all: Call<NetworkProductsAnswer>
}