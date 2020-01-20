package com.aitor.samplemarket.base

import arrow.core.Either
import com.aitor.samplemarket.product.network.NetworkProductsAnswer
import com.aitor.samplemarket.product.network.ProductService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(BASE_URL: String) {

    sealed class ApiError {
        data class UnknownError(val code: Int) : ApiError()
        object NotFoundError : ApiError()
        object NetworkError : ApiError()
    }

    private val productService: ProductService

    init {
        val okHttpClient = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS).addInterceptor(DefaultHeadersInterceptor()).build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()

        productService = retrofit.create(ProductService::class.java)
    }

    val allProducts: Either<ApiError, NetworkProductsAnswer>
        get() = productService.all.safeExecute()

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
