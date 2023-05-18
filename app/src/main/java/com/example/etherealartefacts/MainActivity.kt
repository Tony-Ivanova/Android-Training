package com.example.etherealartefacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.pages.LoginPage
import com.example.etherealartefacts.pages.ProductDetailsPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val (canPop, setCanPop) = remember { mutableStateOf(false) }

            navController.addOnDestinationChangedListener { controller, _, _ ->
                setCanPop(controller.previousBackStackEntry != null)
            }

            Surface {
                Scaffold(
                    topBar = {
                        if (canPop) {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = {}) {
                                        // just in case: IconButton(onClick = navController::popBackStack) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                    }
                                },
                                title = { Text(text = "Item") },
                                actions = {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            Icons.Default.ShoppingCart,
                                            contentDescription = "Shopping Cart"
                                        )
                                    }
                                },
                                backgroundColor = Color.Transparent,
                                elevation = 0.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(it)
                    ) {
                        composable(route = "login") {
                            LoginPage(navController = navController)
                        }
                        composable("product") {
                            val jwtToken =
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNjgyNTM3NzEwLCJleHAiOjE2ODUxMjk3MTB9.OD0k7cmzcgxoUcRGv9Vt0pmLLPxZmYTNbOlxLyCZzqg"
                            ProductDetailsPage(jwtToken)
                        }
                    }
                }
            }
        }
    }
}