package com.paymentpage.ui.msdk.sample.ui.navigation


import androidx.compose.runtime.Composable
import com.paymentpage.ui.msdk.sample.domain.ui.base.viewUseCase
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewUC
import com.paymentpage.ui.msdk.sample.ui.base.ComposeViewState

@Composable
fun NavigationState(
    startRoute: NavRoutes
) {
    val viewUseCase: NavigationViewUC = viewUseCase("Navigation", {
        NavigationViewUC(
            startRoute = startRoute
        )
    })

    ComposeViewState(
        viewUseCase = viewUseCase,
    ) { viewState, viewIntentListener ->
        NavigationScreen(viewState, viewIntentListener)
    }

}
