package com.example.etherealartefacts.ui.theme.product

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.cart.CartViewModel
import com.example.etherealartefacts.ui.theme.productDescription
import com.example.etherealartefacts.ui.theme.topbar.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun OrderHistory(
    destinationsNavigator: DestinationsNavigator, cartViewModel: CartViewModel
) {
    val dividerDimen = dimensionResource(id = R.dimen.divider_size)
    val orderHistory = cartViewModel.orderHistory.collectAsState()
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    Scaffold(topBar = {
        val isBack = true
        TopBar(
            isBack,
            title = stringResource(id = R.string.order_history),
            destinationsNavigator = destinationsNavigator

        )
    }) {

        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            Column(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.row_px),
                    end = dimensionResource(id = R.dimen.row_px),
                )

            ) {
                Column {

                    for (order in orderHistory.value) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column() {
                                Text(
                                    text = "${order.orderedProducts.size} items",
                                    style = Typography.productDescription
                                )
                                Text(
                                    text = "Date: ${dateFormatter.format(order.orderDate)}",
                                    style = Typography.productDescription
                                )
                            }

                            Text(
                                text = "Total ${order.totalPrice}",
                                style = Typography.productDescription
                            )
                        }
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
        }
    }
}

