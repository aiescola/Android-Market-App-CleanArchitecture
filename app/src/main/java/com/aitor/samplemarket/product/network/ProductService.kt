package com.aitor.samplemarket.product.network

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @get:GET(value = "/bins/4bwec")
    val all: Call<NetworkProductsAnswer>
}