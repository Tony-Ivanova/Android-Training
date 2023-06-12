package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.models.request.Products
import com.example.etherealartefacts.models.response.LogInResponse
import com.example.etherealartefacts.models.response.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @POST(value = "auth/local")
    suspend fun login(@Body request: LoginRequest): Response<LogInResponse>

    @GET(value = "products/{productId}?populate=*")
    suspend fun getProduct(@Path("productId") id: Int): ProductWithCategory

    @GET(value = "products?populate=*")
    suspend fun getAllProduct(): Products

    @GET(value = "products?populate=*")
    suspend fun getAllProductJsonResponse(): Response<List<ProductWithCategory>>
}