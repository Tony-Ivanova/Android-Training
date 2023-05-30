package com.example.etherealartefacts.models

data class Users(val results: List<UserModel>)

data class UserModel(val gender: String, val name: NameModel)

data class NameModel(val title: String, val first: String, val last: String)

data class LoginRequest(val identifier: String, val password: String)

data class LogInResponse(
    val jwt: String?,
    val user: User?,
    val error: ErrorResponse?
)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean
)

data class ErrorResponse(
    val status: Int,
    val name: String,
    val message: String,
    val details: Map<String, Any>
)