package com.example.etherealartefacts.ui.theme.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Purple
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.cart.CartViewModel
import com.example.etherealartefacts.ui.theme.destinations.HomePageDestination
import com.example.etherealartefacts.ui.theme.destinations.LoginPageDestination
import com.example.etherealartefacts.ui.theme.destinations.OrderHistoryDestination
import com.example.etherealartefacts.ui.theme.emptyCart
import com.example.etherealartefacts.ui.theme.loginButton
import com.example.etherealartefacts.ui.theme.productDescription
import com.example.etherealartefacts.ui.theme.topbar.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProfilePage(
    destinationsNavigator: DestinationsNavigator, cartViewModel: CartViewModel
) {
    val dividerDimen = dimensionResource(id = R.dimen.divider_size)
    val viewModel: ProfileViewModel = hiltViewModel()

    Scaffold(topBar = {
        val isBack = true
        TopBar(
            isBack,
            title = stringResource(id = R.string.profile),
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.profile_top150))
                ) {
                    IconButton(
                        onClick = { destinationsNavigator.navigate(HomePageDestination) },
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(dimensionResource(id = R.dimen.profileIcon_size)),
                            painter = painterResource(R.drawable.profile),
                            contentDescription = stringResource(id = R.string.profile_picture),
                            tint = Purple.SoftLilac
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 40.dp),
                        text = stringResource(id = R.string.email_hardcoded),
                        style = Typography.emptyCart
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = dimensionResource(id = R.dimen.divider_thickness),
                        modifier = Modifier.padding(
                            top = dividerDimen, bottom = dividerDimen
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { destinationsNavigator.navigate(OrderHistoryDestination) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.order_history),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.orderHistory_icon))
                            )

                            Text(
                                text = stringResource(id = R.string.order_history),
                                style = Typography.productDescription,
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.orderHistory_text_pl))
                            )
                        }

                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = stringResource(id = R.string.order_history),
                            modifier = Modifier.size(dimensionResource(id = R.dimen.orderHistory_icon))
                        )
                    }

                }

                Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.logout_pb))) {
                    Button(
                        onClick = {
                            viewModel.logout()
                            cartViewModel.clearOrderHistory()
                            destinationsNavigator.navigate(LoginPageDestination)
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
                            text = stringResource(id = R.string.logout),
                            style = Typography.loginButton
                        )
                    }
                }
            }
        }
    }
}

