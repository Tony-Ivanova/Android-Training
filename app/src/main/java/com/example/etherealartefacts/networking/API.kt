package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.LogInResponse
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.Product
import com.example.etherealartefacts.models.Users
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @GET(value = "/api")
    suspend fun getRandomUser(): Users

    @POST(value = "/api/auth/local")
    suspend fun getLoggedInUser(@Body request: LoginRequest): LogInResponse

    @GET("api/products/{productId}")
    suspend fun getProduct(
        @Header("Authorization") jwtToken: String,
        @Path("productId") productId: Int
    ): Product
}