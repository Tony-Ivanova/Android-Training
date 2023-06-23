package com.example.etherealartefacts.models.request

import java.util.Date

data class Order(
    val orderedProducts: List<Product>, val orderDate: Date, val totalPrice: Int
)