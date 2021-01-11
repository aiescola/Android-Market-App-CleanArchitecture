package com.aitor.samplemarket.data.network.service

import com.aitor.samplemarket.data.network.model.NetworkDiscount
import retrofit2.Call
import retrofit2.http.GET

interface DiscountService {
    @get:GET(value = "/old/api/discounts")
    val all: Call<NetworkDiscount>
}