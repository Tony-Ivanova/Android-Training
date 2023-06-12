package com.example.etherealartefacts.main

import HomePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.ui.theme.login.LoginPage
import com.example.etherealartefacts.ui.theme.product.ProductDetailsPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var jwtTokenProvider: JWTTokenProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val (canPop, setCanPop) = remember { mutableStateOf(false) }

            navController.addOnDestinationChangedListener { controller, _, _ ->
                setCanPop(controller.previousBackStackEntry != null)
            }

            Surface {
                Scaffold() { it ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(it)
                    ) {
                        composable(route = "login") {
                            LoginPage(jwtTokenProvider, navController = navController)
                        }
                        composable("products/{productId}?populate=*") {
                            val productId = it.arguments?.getString("productId")?.toIntOrNull()
                            if (productId !== null) {
                                ProductDetailsPage(productId, navController = navController)
                            } else {
                                Text("Invalid product ID")
                            }

                        }
                        composable("home") {
                            HomePage(navController = navController)
                        }
                    }
                }
            }
        }
    }
}