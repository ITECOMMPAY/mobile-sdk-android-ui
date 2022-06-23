package com.ecommpay.msdk.ui.entry

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewActions
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

@Composable
fun EntryState(
    entryViewModel: EntryViewModel = viewModel(),
    navController: NavHostController,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<EntryViewData> by entryViewModel.viewState.observeAsState(DefaultViewStates.Loading(entryViewModel.defaultViewData()))
    val viewAction: ViewActions? by entryViewModel.viewAction.observeAsState()

    LaunchedEffect(key1 = Unit) {
        entryViewModel.entryPoint()
    }
    EntryScreen(
        state = state,
        intentListener = { intent -> entryViewModel.pushIntent(intent) }
    )
    viewAction?.Invoke {
        when (viewAction) {
            is DefaultViewActions -> defaultActionListener(viewAction as DefaultViewActions)
        }
    }
}