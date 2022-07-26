package com.paymentpage.ui.msdk.sample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paymentpage.ui.msdk.sample.MainActivity


@Composable
fun App(activity: MainActivity) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") {
            MainScreen(activity = activity)
        }
    }
}
