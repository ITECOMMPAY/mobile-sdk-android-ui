@file:OptIn(ExperimentalAnimationApi::class)

package com.ecommpay.msdk.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ecommpay.msdk.ui.presentation.init.InitScreen
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.ecommpay.msdk.ui.presentation.result.ResultScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun NavigationComponent(
    navigator: Navigator
) {
    val navController = rememberAnimatedNavController()
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { navController.navigate(it.name) }.launchIn(this)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Navigator.NavTarget.Init.name,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(700)
            )
        }
    ) {
        composable(route = Navigator.NavTarget.Init.name) {
            InitScreen(navigator = navigator)
        }
        composable(route = Navigator.NavTarget.Main.name) {
            MainScreen(navigator = navigator)
        }
        composable(route = Navigator.NavTarget.Result.name) {
            ResultScreen()
        }

    }
}