package com.paymentpage.ui.msdk.sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainScreen
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldScreen
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.RecurrentScreen


@Composable
fun NavigationComponent(
    listener: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main.route,
    ) {
        composable(NavRoutes.Main.route) {
            MainScreen(navController = navController, listener = listener)
        }
        composable(NavRoutes.AdditionalFields.route) {
            AdditionalFieldScreen(navController = navController)
        }
        composable(NavRoutes.Recurrent.route) {
            RecurrentScreen(navController = navController)
        }
    }
}