package com.example.etherealartefacts.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.response.LoginRequest
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginResult {
    data class LoginSuccess(val jwtToken: String) : LoginResult()
    data class LoginError(val errorMessage: String) : LoginResult()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultRepository,
    private val jwtTokenProvider: JWTTokenProvider
) : ViewModel() {

    private val _login = MutableStateFlow(false)
    val login = _login.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun login(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val loginResponse = repository.getLoggedInUser(request)
                if (loginResponse.isSuccessful) {
                    val loginResult = LoginResult.LoginSuccess(loginResponse.body()?.jwt ?: "")
                    val jwtToken = loginResult.jwtToken
                    jwtTokenProvider.token = jwtToken
                    _login.value = true;
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
