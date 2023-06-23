package com.example.etherealartefacts.ui.theme.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.destinations.CartPageDestination
import com.example.etherealartefacts.ui.theme.destinations.ProfilePageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TopBar(
    isBack: Boolean,
    title: String,
    destinationsNavigator: DestinationsNavigator,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.px_15),
                end = dimensionResource(id = R.dimen.px_15)
            )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },

            navigationIcon = {
                if (isBack) {
                    IconButton(onClick = { destinationsNavigator.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                } else {
                    Spacer(
                        modifier = Modifier.fillMaxHeight()
                    )
                }

            },
            actions = {
                if (title == stringResource(id = R.string.home)) {
                    IconButton(
                        onClick = {
                            destinationsNavigator.navigate(ProfilePageDestination)

                        },
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.profile),
                        )
                    }
                }
                IconButton(
                    onClick = {
                        destinationsNavigator.navigate(CartPageDestination)

                    },
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = stringResource(id = R.string.shopping_cart),
                    )
                }
            },
        )
    }
}
