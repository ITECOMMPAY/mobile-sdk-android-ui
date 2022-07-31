package com.paymentpage.ui.msdk.sample.ui.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRouts
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.colorPicker.BrandColorPicker
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.imagesList.ExpandableSelectImagesList
import com.paymentpage.ui.msdk.sample.utils.collectAsEffect

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController,
    listener: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        null -> viewModel.pushIntent(MainViewIntents.Init)
    }
    viewModel.viewAction.collectAsEffect { viewAction ->
        when (viewAction) {
            is NavRouts -> navController.navigate(viewAction.route)
            is MainViewActions.Sale -> listener()
        }
    }
    val padding = 10.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        BrandTitle()
        Spacer(modifier = Modifier.size(padding))
        BrandColorPicker()
        Spacer(modifier = Modifier.size(padding))
        ProjectSettings()
        Spacer(modifier = Modifier.size(padding))
        PaymentData()
        Spacer(modifier = Modifier.size(padding))
        ExpandableSelectImagesList()
        Spacer(modifier = Modifier.size(padding))
        AdditionalFieldsButton(navController)
        Spacer(modifier = Modifier.size(padding))
        ApiHostCheckbox()
        Spacer(modifier = Modifier.size(padding))
        GooglePayCheckbox()
        Spacer(modifier = Modifier.size(padding))
        MockModeCheckbox()
        Spacer(modifier = Modifier.size(padding))
        SaleButton(listener)
    }
}