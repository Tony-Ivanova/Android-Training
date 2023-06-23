package com.example.etherealartefacts.models.request

data class Category(
    val id: Int, val name: String, var isChecked: Boolean = true
)