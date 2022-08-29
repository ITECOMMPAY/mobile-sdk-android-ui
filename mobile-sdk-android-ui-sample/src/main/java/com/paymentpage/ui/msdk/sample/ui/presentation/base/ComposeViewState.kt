package com.paymentpage.ui.msdk.sample.ui.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewActions
import com.paymentpage.ui.msdk.sample.utils.CollectAsEffect

@Composable
fun <VI : ViewIntents, VS : ViewState> ComposeViewState(
    navController: NavController,
    viewModel: BaseViewModel<VI, VS>,
    mainViewActionListener: (MainViewActions) -> Unit,
    content: @Composable (VS, (VI) -> Unit) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()

    if (viewState == null) viewModel.pushSideEffect(ViewSideEffects.Init)

    LaunchedEffect(key1 = Unit) {
        if (viewState != null)
            viewModel.pushSideEffect(ViewSideEffects.Launched)
    }

    viewModel.viewAction.CollectAsEffect { viewAction ->
        when (viewAction) {
            is NavRoutes.Back -> navController.popBackStack()
            is NavRoutes -> navController.navigate(viewAction.route)
            is MainViewActions -> mainViewActionListener(viewAction)
        }
    }
    viewState?.let { content(it) { intent -> viewModel.pushIntent(intent) } }
}