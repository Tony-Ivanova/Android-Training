package com.example.etherealartefacts.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.response.LogInResponse
import com.example.etherealartefacts.models.response.LoginRequest
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginResult {
    data class LoginSuccess(val jwtToken: String) : LoginResult()
    data class LoginError(val errorMessage: String) : LoginResult()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {

    private val _response = MutableStateFlow<Result<LogInResponse>?>(null)

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult: StateFlow<LoginResult?> = _loginResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun login(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val loginResponse = repository.getLoggedInUser(request)
                if (loginResponse.isSuccessful) {
                    _loginResult.value = LoginResult.LoginSuccess(loginResponse.body()?.jwt ?: "")
                } else {
                    _loginResult.value = LoginResult.LoginError(loginResponse.message())
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult.LoginError(e.message ?: "An error occurred")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
