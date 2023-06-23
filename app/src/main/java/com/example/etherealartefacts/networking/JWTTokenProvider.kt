package com.example.etherealartefacts.networking

class JWTTokenProvider(
    var jwtToken: String? = null
) {
    var isLoggedIn: Boolean = false
        private set

    var token: String?
        get() = jwtToken
        set(value) {
            jwtToken = value
            isLoggedIn = true
        }

    fun clearToken() {
        jwtToken = null
        isLoggedIn = false
    }
}