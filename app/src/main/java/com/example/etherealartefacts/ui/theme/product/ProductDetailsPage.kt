package com.example.etherealartefacts.ui.theme.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Purple
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.cart.CartViewModel
import com.example.etherealartefacts.ui.theme.loginButton
import com.example.etherealartefacts.ui.theme.productCategory
import com.example.etherealartefacts.ui.theme.productDescription
import com.example.etherealartefacts.ui.theme.productPrice
import com.example.etherealartefacts.ui.theme.productRating
import com.example.etherealartefacts.ui.theme.productTitle
import com.example.etherealartefacts.ui.theme.topbar.TopBar
import com.example.etherealartefacts.ui.theme.utils.showToastMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.DecimalFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProductDetailsPage(
    productId: Int, destinationsNavigator: DestinationsNavigator, cartViewModel: CartViewModel
) {
    val viewModel: ProductViewModel = hiltViewModel()
    val product by viewModel.productState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage = stringResource(id = R.string.fetch_error)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productId?.let { id ->
            viewModel.getProductDetails(id)
        }
    }

    LaunchedEffect(isError) {
        if (isError) {
            showToastMessage(context, errorMessage)
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    product?.let { product ->
        Scaffold(
            topBar = {
                val isBack = true

                TopBar(
                    isBack,
                    title = stringResource(id = R.string.cart),
                    destinationsNavigator = destinationsNavigator
                )
            },
        ) {
            Box {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.background),
                    contentDescription = stringResource(id = R.string.background_loginP_cd),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.productPage_px),
                        end = dimensionResource(id = R.dimen.productPage_px)
                    )
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(product?.image),
                            contentDescription = stringResource(id = R.string.product_picture),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.productPicture_width))
                                .height(dimensionResource(id = R.dimen.productPicture_height))
                                .clip(
                                    RoundedCornerShape(dimensionResource(id = R.dimen.productPicture_curve))
                                )
                        )
                    }

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(id = R.dimen.title_pt)),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = (product?.title
                                        ?: stringResource(id = R.string.no_title)),
                                    style = Typography.productTitle
                                )

                                Text(
                                    text = stringResource(id = R.string.Category) + (product?.category
                                        ?: stringResource(id = R.string.no_short_description)),
                                    style = Typography.productCategory
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                product?.rating?.let { rating ->
                                    if (rating > 0) {
                                        Text(
                                            text = rating.toString(),
                                            style = Typography.productRating
                                        )
                                        repeat(rating) {
                                            Icon(
                                                Icons.Default.Star,
                                                contentDescription = stringResource(id = R.string.rating),
                                                modifier = Modifier.size(dimensionResource(id = R.dimen.ratingStars_size))
                                            )
                                        }
                                    } else {
                                        Text(stringResource(id = R.string.no_rating))
                                    }
                                } ?: Text(stringResource(id = R.string.no_rating))
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(id = R.dimen.description_pt))
                        ) {
                            Text(
                                text = (product?.description
                                    ?: stringResource(id = R.string.no_description)),
                                maxLines = integerResource(id = R.integer.description_maxLines),
                                overflow = TextOverflow.Ellipsis,
                                style = Typography.productDescription
                            )
                        }

                        val decimalFormat = DecimalFormat("0.00")
                        Text(
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.productPrice_pt)),
                            text = "${stringResource(id = R.string.dollar_sign)}  ${
                                decimalFormat.format(
                                    product?.price
                                )
                            }",
                            style = Typography.productPrice
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(
                                    top = dimensionResource(
                                        id = R.dimen.addToCartButton_pt
                                    )
                                )
                        ) {

                            Button(
                                onClick = {
                                    cartViewModel.addToOrderedProducts(product)
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
                                    text = stringResource(id = R.string.add_to_cart_btn),
                                    style = Typography.loginButton
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}
