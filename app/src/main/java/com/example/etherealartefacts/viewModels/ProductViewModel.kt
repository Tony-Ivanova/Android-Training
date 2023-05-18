package com.example.etherealartefacts.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.DefaultRepository
import com.example.etherealartefacts.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    var product = mutableStateOf<Product?>(null)

    suspend fun getProduct(productId: Int, jwtToken: String) {
        val authorization = "Bearer $jwtToken"
        val response = repository.getProduct(productId, authorization)

        response?.let {
            product.value = it
        }

    }
}
