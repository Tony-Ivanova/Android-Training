package com.example.etherealartefacts.ui.theme.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginResult {
    data class LoginSuccess(val jwtToken: String) : LoginResult()
    data class LoginError(val errorMessage: String) : LoginResult()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DefaultRepository,
    private val jwtTokenProvider: JWTTokenProvider
) : ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            jwtTokenProvider.clearToken()
        }
    }
}
