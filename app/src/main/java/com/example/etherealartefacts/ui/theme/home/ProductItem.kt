package com.example.etherealartefacts.ui.theme.home

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.productCategory
import com.example.etherealartefacts.ui.theme.productRating
import com.example.etherealartefacts.ui.theme.productShortDescription
import com.example.etherealartefacts.ui.theme.productTitle

@Composable
fun ProductItem(product: ProductWithCategory, navController: NavHostController) {

    Box(modifier = Modifier
        .width(353.dp)
        .clickable { navController.navigate("products/${product.id}?populate=*") }) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.productImage_size))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.productPicture_curve))),
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = stringResource(id = R.string.product_picture),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.productImage_size))
                    .padding(start = dimensionResource(id = R.dimen.productColumns_space)),
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                Text(
                    style = Typography.productCategory,
                    text = stringResource(id = R.string.Category) + product.category,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    style = Typography.productTitle,
                    text = product.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    style = Typography.productShortDescription,
                    text = (product.short_description ?: stringResource(id = R.string.no_title))
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        style = Typography.productRating, text = product.rating.toString()
                    )
                    repeat(product.rating.toInt()) {
                        Icon(
                            Icons.Default.Star,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.ratingStars_size)),
                            contentDescription = stringResource(id = R.string.rating),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                Text(
                    style = Typography.productCategory,
                    text = stringResource(id = R.string.dollar_sign) + product.price.toString()
                )
            }
        }
    }
}