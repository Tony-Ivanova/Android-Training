package com.example.etherealartefacts.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.viewModels.ProductViewModel


@Composable
fun ProductDetailsPage(jwtToken: String) {

    val viewModel: ProductViewModel = hiltViewModel()
    val product = viewModel.product.value

    LaunchedEffect(Unit) {
        viewModel.getProduct(2, jwtToken)
    }

    product?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFE1D5ED)), startY = 300f, endY = 500f
                    )
                )
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = "Item Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = (it.title ?: "No title available"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = (it.shortDescription ?: "No short description available"),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Row {
                        if (it.rating > 0) {
                            repeat(it.rating) {
                                Icon(Icons.Default.Star, contentDescription = "Rating")
                            }
                        } else {
                            Text("No rating available")
                        }
                    }
                }

                Text(
                    text = (it.description ?: "No description available"),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "$" + it.price.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(40.dp)
                        .width(250.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "+ Add to Cart")
                }
            }
        }
    }

}