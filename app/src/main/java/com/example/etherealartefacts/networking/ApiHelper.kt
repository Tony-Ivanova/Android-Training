package com.example.etherealartefacts.networking

import com.example.etherealartefacts.models.LogInResponse
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.Product
import com.example.etherealartefacts.models.Users

interface ApiHelper {
    suspend fun getRandomUser(): Users
    suspend fun getLoggedInUser(request: LoginRequest): LogInResponse
    suspend fun getProduct(productId: Int, jwtToken: String): Product
}