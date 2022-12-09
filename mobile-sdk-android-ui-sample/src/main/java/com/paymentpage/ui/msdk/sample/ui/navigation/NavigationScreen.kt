package com.paymentpage.ui.msdk.sample.ui.navigation

import androidx.compose.runtime.Composable
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewState
import com.paymentpage.ui.msdk.sample.ui.additionalFields.AdditionalFieldsState
import com.paymentpage.ui.msdk.sample.ui.main.MainState
import com.paymentpage.ui.msdk.sample.ui.recurrent.RecurrentState
import com.paymentpage.ui.msdk.sample.ui.threeDSecure.ThreeDSecureState
import kotlin.system.exitProcess

@Composable
fun NavigationScreen(
    viewState: NavigationViewState,
    listener: (NavigationViewIntents) -> Unit
) {
    when (val currentRoute = viewState.currentRoute) {
        is MainHostScreens.MainScreen -> MainState(route = currentRoute)
        is MainHostScreens.AdditionalFields -> AdditionalFieldsState(route = currentRoute)
        is MainHostScreens.Recurrent -> RecurrentState(route = currentRoute)
        is MainHostScreens.ThreeDSecure -> ThreeDSecureState(route = currentRoute)
        is NavRoutes.Exit -> exitProcess(0)
    }
}