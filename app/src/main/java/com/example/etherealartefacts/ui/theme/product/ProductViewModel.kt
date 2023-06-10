package com.example.etherealartefacts.ui.theme.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.repository.DefaultRepository
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.models.request.ProductWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _response = MutableStateFlow<Result<ProductWithCategory>?>(null)
    val response : StateFlow<Result<ProductWithCategory>?> = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val getProductDetailsResponse = repository.getProduct(id)
                _response.value = Result.success(getProductDetailsResponse)
            } catch (e: Exception) {
                _response.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}