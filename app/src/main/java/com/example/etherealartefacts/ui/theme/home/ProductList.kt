package com.example.etherealartefacts.ui.theme.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.ProductWithCategory
import java.util.Locale

@Composable
fun ProductList(
    products: List<ProductWithCategory>,
    searchQuery: MutableState<TextFieldValue>,
    navController: NavHostController
) {

    var filteredProducts: List<ProductWithCategory>

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        val searchedText = searchQuery.value.text

        filteredProducts = if (searchedText.isEmpty()) {
            products
        } else {
            val resultList = ArrayList<ProductWithCategory>()

            for (product in products) {
                if (product.title.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(product)
                }
            }

            resultList
        }

        items(filteredProducts) { product ->
            val dividerDimen = dimensionResource(id = R.dimen.divider_size)
            ProductItem(product = product, navController)
            Divider(
                color = Color.Gray,
                thickness = dimensionResource(id = R.dimen.divider_thickness),
                modifier = Modifier.padding(
                    top = dividerDimen,
                    bottom = dividerDimen
                )
            )
        }
    }
}