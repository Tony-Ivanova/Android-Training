package com.example.etherealartefacts.ui.theme.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.ui.theme.NavGraphs
import com.example.etherealartefacts.ui.theme.cart.CartViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.rememberNavHostEngine

@Composable
fun AppNavigation(viewModelStoreOwner: ViewModelStoreOwner) {
    val navController = rememberNavController()
    val navHostEngine = rememberNavHostEngine()

    DestinationsNavHost(
        startRoute = NavGraphs.home,
        engine = navHostEngine,
        navController = navController,
        dependenciesContainerBuilder = {
            dependency(hiltViewModel<CartViewModel>(viewModelStoreOwner))
        },
        navGraph = NavGraphs.root
    )
}