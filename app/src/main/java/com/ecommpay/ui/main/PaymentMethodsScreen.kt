package com.ecommpay.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.ui.base.DefaultViewStates
import com.ecommpay.ui.base.ViewActions
import com.ecommpay.ui.base.ViewStates
import com.ecommpay.ui.main.itemPaymentMethod.ItemPaymentMethod
import com.ecommpay.ui.main.itemPaymentMethod.ItemPaymentMethodViewData

@Composable
@Preview
fun PaymentMethodsScreen(mainViewModel: MainViewModel = viewModel()) {
    val state: ViewStates<MainViewData>? by mainViewModel.viewState.observeAsState()
    val action: ViewActions? by mainViewModel.viewAction.observeAsState()
    LaunchedEffect(key1 = Unit) {
        mainViewModel.entryPoint()
    }

    when (action) {
        is MainViewActions.ShowToast -> {
            val context = LocalContext.current
            (action as MainViewActions.ShowToast).invoke {
                Toast.makeText(
                    context,
                    (action as MainViewActions.ShowToast).message,
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    when (state) {
        is DefaultViewStates.Display -> {
            PaymentMethodsList((state as DefaultViewStates.Display<MainViewData>).viewData.paymentMethodList) { itemIntent ->
                mainViewModel.pushIntent(itemIntent)
            }
        }
        is DefaultViewStates.Loading -> {

        }
    }

}

@Composable
fun PaymentMethodsList(
    paymentMethodList: List<ItemPaymentMethodViewData>,
    listener: (intent: MainViewIntents) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            paymentMethodList.forEach {
                ItemPaymentMethod(
                    name = it.name,
                    icon = it.icon,
                    listener = listener)
            }
        }
    }
}