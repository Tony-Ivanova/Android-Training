package com.example.etherealartefacts.ui.theme.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Purple
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.destinations.HomePageDestination
import com.example.etherealartefacts.ui.theme.emptyCart
import com.example.etherealartefacts.ui.theme.loginButton
import com.example.etherealartefacts.ui.theme.topbar.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CartPage(
    destinationsNavigator: DestinationsNavigator, cartViewModel: CartViewModel
) {
    val orderedProducts = cartViewModel.orderedProducts.collectAsState()
    val totalSum = cartViewModel.totalPrice.collectAsState()

    Scaffold(topBar = {
        val isBack = true
        TopBar(
            isBack,
            title = stringResource(id = R.string.cart),
            destinationsNavigator = destinationsNavigator

        )
    }) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.row_px),
                        end = dimensionResource(id = R.dimen.row_px),
                    )
                    .fillMaxSize()
            ) {
                if (orderedProducts.value.size == 0) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.shoppingCart_pt))
                    ) {
                        IconButton(
                            onClick = { destinationsNavigator.navigate(HomePageDestination) },
                        ) {
                            Icon(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(dimensionResource(id = R.dimen.shoppingCart_size)),
                                painter = painterResource(R.drawable.empty_shopping_cart),
                                contentDescription = stringResource(id = R.string.shopping_cart),
                                tint = Purple.SoftLilac
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.emptyCart_pt)),
                            text = stringResource(id = R.string.emptyCart),
                            style = Typography.emptyCart
                        )
                    }
                } else {
                    LazyColumn() {
                        orderedProducts.value?.let {
                            items(orderedProducts.value) { product ->
                                val dividerDimen = dimensionResource(id = R.dimen.divider_size)
                                OrderedItem(product, destinationsNavigator, cartViewModel)
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

                    Row() {
                        Row() {
                            Text(text = "Items: ${orderedProducts.value.size}")
                        }
                        Row() {
                            Text(text = "Total: ${totalSum.value}")
                        }
                    }

                    Button(
                        onClick = {
                            cartViewModel.placeOrder()
                            destinationsNavigator.navigate(HomePageDestination)
                        },
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.addToCartButton_height))
                            .width(dimensionResource(id = R.dimen.addToCartButton_width))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.addToCartButton_px))),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple.DarkIndigo,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.checkOut),
                            style = Typography.loginButton
                        )
                    }
                }

            }
        }
    }
}
