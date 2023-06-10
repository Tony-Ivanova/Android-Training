package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.models.request.Products
import com.example.etherealartefacts.models.response.LogInResponse
import com.example.etherealartefacts.models.response.LoginRequest
import retrofit2.Response

interface ApiHelper {
    suspend fun getLoggedInUser(request: LoginRequest): Response<LogInResponse>
    suspend fun getProduct(productId: Int): ProductWithCategory
    suspend fun getAllProduct(): Products

    suspend fun getAllProductJsonResponse():  Response<List<ProductWithCategory>>

}