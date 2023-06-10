package com.example.etherealartefacts.models.request

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val short_description: String,
    val stock: Int,
    val price: Int,
    val rating: Int,
    val image: String
)