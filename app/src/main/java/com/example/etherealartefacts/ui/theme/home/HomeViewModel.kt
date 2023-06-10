package com.example.etherealartefacts.ui.theme.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.models.request.Products
import com.example.etherealartefacts.repository.DefaultRepository
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _response = MutableStateFlow<Result<List<ProductWithCategory>?>>(Result.success(emptyList()))
    val response: StateFlow<Result<List<ProductWithCategory>?>> = _response


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response: Response<List<ProductWithCategory>> = repository.getAllProductJsonResponse()
                Log.d("TAG", "Response body: ${response.body()?.toString()}")

                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()
                    _response.value = Result.success(products)
                } else {
                    _response.value = Result.failure(Exception("Error while fetching data"))
                }
            } catch (e: Exception) {
                _response.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
