package com.aitor.samplemarket.data.network

import okhttp3.Interceptor

class DefaultHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json").build()
        return chain.proceed(request)
    }
}