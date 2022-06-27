@file:OptIn(ExperimentalAnimationApi::class)

package com.ecommpay.msdk.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { navController.navigate(it.name) }.launchIn(this)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Navigator.NavTarget.Main.name,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Navigator.NavTarget.Main.name) {
            MainScreen()
        }
    }
}