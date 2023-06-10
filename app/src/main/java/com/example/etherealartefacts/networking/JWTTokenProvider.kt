package com.example.etherealartefacts.networking

class JWTTokenProvider(
    private var jwtToken: String? = null,
    private var isLoggedIn: Boolean = false
) {

    fun setJwtToken(token: String) {
        jwtToken = token
        isLoggedIn = true
    }

    fun getJwtToken(): String? {
        return jwtToken
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }

    fun clearToken() {
        jwtToken = null
        isLoggedIn = false
    }
}