package com.example.etherealartefacts.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.DefaultRepository
import com.example.etherealartefacts.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var loginError = mutableStateOf("")
    var jwtToken = mutableStateOf("")

    suspend fun getLoggedInUser(email: String, password: String) {
        isLoading.value = true

        val request = LoginRequest(email, password)
        val response = repository.getLoggedInUser(request)

        isLoading.value = false

        if (response.error?.message == "Invalid identifier or password") {
            loginError.value = "Invalid identifier or password"
        } else if (response.error != null) {
            loginError.value = response.error.message
        } else if (response.jwt != null) {
            jwtToken.value = response.jwt
        }
    }
}