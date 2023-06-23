package com.example.etherealartefacts.ui.theme.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.cartProductQuantity
import com.example.etherealartefacts.ui.theme.cartProductTitle
import com.example.etherealartefacts.ui.theme.destinations.ProductDetailsPageDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun OrderedItem(
    product: Product, destinationsNavigator: DestinationsNavigator, cartViewModel: CartViewModel
) {
    Box(modifier = Modifier
        .width(dimensionResource(id = R.dimen.productBox_width))
        .clickable {
            destinationsNavigator.navigate(
                ProductDetailsPageDestination(product.id)
            )
        }) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.cartProduct_height))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.cartProduct_curve))),
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = stringResource(id = R.string.product_picture),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.cartProduct_height))
                    .padding(start = dimensionResource(id = R.dimen.productColumns_space)),
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                Text(
                    style = Typography.cartProductTitle,
                    text = product.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    style = Typography.cartProductTitle,
                    text = "${stringResource(id = R.string.dollar_sign)} ${product.price}"
                )
                Text(
                    style = Typography.cartProductQuantity,
                    text = "${stringResource(id = R.string.quantity)} ${
                        cartViewModel.getQuantityForProduct(
                            product.id
                        )
                    }"
                )
            }
            Column() {
                IconButton(onClick = { cartViewModel.removeProduct(product) }) {
                    Icon(Icons.Default.Close, contentDescription = "Remove")
                }
            }
        }
    }
}