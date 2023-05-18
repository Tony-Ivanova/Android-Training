package com.example.etherealartefacts.models

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val shortDescription: String,
    val stock: Int,
    val price: Double,
    val rating: Int,
    val image: String
)