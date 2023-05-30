package com.example.etherealartefacts.networking

import com.example.etherealartefacts.viewModels.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import  java.util.concurrent.TimeUnit

class APIClient(private val token: String, private val loginViewModel: LoginViewModel) {
    private val DEFAULT_TIMEOUT = 1L

    val defaultService: API

    init {
        defaultService = createService()
    }

    // User and Auth service
    private fun createService(): API {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(token))
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                val jwtToken = loginViewModel.jwtToken.value
                if (jwtToken.isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $jwtToken")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://ethereal-artefacts.fly.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client.create(API::class.java)
    }
}