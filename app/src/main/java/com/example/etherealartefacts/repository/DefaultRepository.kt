package com.example.etherealartefacts.repository

import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.models.request.Products
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

    override suspend fun getProduct(productId: Int): ProductWithCategory {
        return apiService.getProduct(productId)
    }

    override suspend fun getAllProduct(): Products {
        return apiService.getAllProduct()
    }

    override suspend fun getAllProductJsonResponse(): Response<List<ProductWithCategory>> {
        return apiService.getAllProductJsonResponse()
    }
}
