package com.aitor.samplemarket.data.network.service

import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @get:GET(value = "/api/products")
    val all: Call<NetworkProductsAnswer>
}