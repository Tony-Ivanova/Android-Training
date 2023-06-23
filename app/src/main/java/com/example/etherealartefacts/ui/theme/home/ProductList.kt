package com.example.etherealartefacts.ui.theme.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.Product
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun ProductList(
    displayedProducts: List<Product>, destinationsNavigator: DestinationsNavigator
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(displayedProducts) { product ->
            val dividerDimen = dimensionResource(id = R.dimen.divider_size)
            ProductItem(product = product, destinationsNavigator)
            Divider(
                color = Color.Gray,
                thickness = dimensionResource(id = R.dimen.divider_thickness),
                modifier = Modifier.padding(
                    top = dividerDimen, bottom = dividerDimen
                )
            )
        }
    }
}