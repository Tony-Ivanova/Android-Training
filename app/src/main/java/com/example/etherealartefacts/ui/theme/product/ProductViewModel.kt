package com.example.etherealartefacts.ui.theme.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _productState = MutableStateFlow<Product?>(null)
    val productState = _productState.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response: Response<Product> = repository.getProduct(id)
                if (response.isSuccessful && response.body() != null) {
                    val product = response.body();
                    _productState.value = product!!
                } else {
                    _isError.value = true
                }
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}