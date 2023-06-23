package com.example.etherealartefacts.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("short_description") val shortDescription: String,
    val stock: Int,
    val price: Int,
    val rating: Int,
    val image: String,
    val category: String,
    val quantity: Int = 0,
    val isInStock: Boolean
) : Parcelable