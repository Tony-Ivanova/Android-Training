package com.example.etherealartefacts.repository

import com.example.etherealartefacts.models.request.Category
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.response.LogInResponse
import com.example.etherealartefacts.models.response.LoginRequest
import com.example.etherealartefacts.networking.API
import com.example.etherealartefacts.networking.ApiHelper
import retrofit2.Response

class DefaultRepository(
    private val apiService: API
) : ApiHelper {

    override suspend fun getLoggedInUser(request: LoginRequest): Response<LogInResponse> {
        return apiService.login(request)
    }

    override suspend fun getProduct(productId: Int): Response<Product> {
        return apiService.getProduct(productId)
    }

    override suspend fun getAllProductJsonResponse(): Response<List<Product>> {
        return apiService.getAllProductJsonResponse()
    }

    override suspend fun getAllCategories(): Response<List<Category>> {
        return apiService.getAllCategories()
    }
}
