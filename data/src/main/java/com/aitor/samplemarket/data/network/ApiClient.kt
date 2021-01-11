package com.aitor.samplemarket.data.network

import arrow.core.Either
import com.aitor.samplemarket.data.network.model.NetworkDiscount
import com.aitor.samplemarket.data.network.model.NetworkProductsAnswer
import com.aitor.samplemarket.data.network.service.DiscountService
import com.aitor.samplemarket.data.network.service.ProductService
import com.aitor.samplemarket.domain.model.ApiError
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(BASE_URL: String, isDebug: Boolean) {

    private val productService: ProductService
    private val discountService: DiscountService

    init {
        val okHttpClientBuilder = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS).addInterceptor(DefaultHeadersInterceptor())

        if (isDebug) {
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create()).build()

        productService = retrofit.create(ProductService::class.java)
        discountService = retrofit.create(DiscountService::class.java)
    }

    val allProducts: Either<ApiError, NetworkProductsAnswer>
        get() = productService.all.safeExecute()

    val discounts: Either<ApiError, NetworkDiscount>
        get() = discountService.all.safeExecute()

    private fun <T> Call<T>.safeExecute(): Either<ApiError, T> {
        return try {
            val response = execute()
            parseResponse(response)
        } catch (e: Exception) {
            Either.left(ApiError.NetworkError)
        }
    }

    private fun <T> parseResponse(response: Response<T>): Either<ApiError, T> = when {
        response.code() == 404 -> Either.left(ApiError.NotFoundError)
        response.code() == 400 -> Either.left(
            ApiError.UnknownError(
                response.code()
            )
        )
        response.body() == null -> Either.left(
            ApiError.UnknownError(
                response.code()
            )
        )
        else -> Either.right(response.body()!!)
    }
}
