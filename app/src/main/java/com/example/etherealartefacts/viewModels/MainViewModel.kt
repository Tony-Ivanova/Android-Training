package com.example.etherealartefacts.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.DefaultRepository
import com.example.etherealartefacts.models.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    var users = mutableStateOf(null as Users?)

    suspend fun getRandomUser(): Users {
        return repository.getRandomUser()
    }
}