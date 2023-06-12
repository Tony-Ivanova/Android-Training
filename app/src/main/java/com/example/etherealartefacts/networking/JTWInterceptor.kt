package com.example.etherealartefacts.networking

import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor(private val jwtTokenProvider: JWTTokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (jwtTokenProvider.isLoggedIn() && jwtTokenProvider.getJwtToken() != null) {
            val requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${jwtTokenProvider.getJwtToken()}")
                .build()

            return chain.proceed(requestWithToken)
        }

        return chain.proceed(originalRequest)
    }
}