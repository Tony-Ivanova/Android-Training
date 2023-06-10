package com.example.etherealartefacts.models.request

data class Products(
    val products: List<ProductWithCategory>
)

data class ProductWithCategory(
    val id: Int,
    val title: String,
    val description: String,
    val short_description: String,
    val stock: Int,
    val price: Int,
    val rating: Int,
    val image: String,
    val category: String,
)