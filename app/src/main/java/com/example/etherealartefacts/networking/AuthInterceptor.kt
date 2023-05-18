package com.example.etherealartefacts.networking

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}