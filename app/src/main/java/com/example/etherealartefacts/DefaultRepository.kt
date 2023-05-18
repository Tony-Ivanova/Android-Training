package com.example.etherealartefacts

import com.example.etherealartefacts.models.LogInResponse
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.Product
import com.example.etherealartefacts.models.Users
import com.example.etherealartefacts.networking.API
import com.example.etherealartefacts.networking.ApiHelper

class DefaultRepository(private val apiService: API) : ApiHelper {
    override suspend fun getRandomUser(): Users {
        return apiService.getRandomUser()
    }

    override suspend fun getLoggedInUser(request: LoginRequest): LogInResponse {
        return apiService.getLoggedInUser(request)
    }

    override suspend fun getProduct(productId: Int, jwtToken: String): Product {
        return apiService.getProduct(jwtToken, productId)
    }
}