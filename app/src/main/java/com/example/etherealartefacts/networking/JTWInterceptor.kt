package com.example.etherealartefacts.networking

import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor(private val jwtTokenProvider: JWTTokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (jwtTokenProvider.isLoggedIn && jwtTokenProvider.jwtToken != null) {
            val requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${jwtTokenProvider.jwtToken}").build()

            return chain.proceed(requestWithToken)
        }

        return chain.proceed(originalRequest)
    }
}