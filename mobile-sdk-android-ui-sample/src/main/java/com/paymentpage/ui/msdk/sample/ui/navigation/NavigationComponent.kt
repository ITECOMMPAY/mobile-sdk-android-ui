package com.paymentpage.ui.msdk.sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainScreen
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldScreen


@Composable
fun NavigationComponent(
    listener: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRouts.Main.route,
    ) {
        composable(NavRouts.Main.route) {
            MainScreen(navController = navController, listener = listener)
        }
        composable(NavRouts.AdditionalFields.route) {
            AdditionalFieldScreen(navController = navController)
        }
    }
}