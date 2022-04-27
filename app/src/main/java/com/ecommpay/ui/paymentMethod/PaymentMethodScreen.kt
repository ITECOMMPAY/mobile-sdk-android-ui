package com.ecommpay.ui.paymentMethod

import androidx.activity.compose.BackHandler
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.ui.base.DefaultViewStates
import com.ecommpay.ui.base.ViewStates
import com.ecommpay.ui.navigation.NavigationViewActions
import com.ecommpay.ui.views.ToolBar

@Composable
fun PaymentMethodScreen(
    state: ViewStates<PaymentMethodViewData>,
    intentListener: (intent: PaymentMethodViewIntents) -> Unit,
) {
    BackHandler {
        intentListener(PaymentMethodViewIntents.MoveToPaymentMethodsListView)
    }
    Scaffold(
        topBar = {
            ToolBar {
                intentListener(PaymentMethodViewIntents.MoveToPaymentMethodsListView)
            }
        },
        content = {
            when (state) {
                is DefaultViewStates.Display -> {
                    Text(text = state.viewData.title)
                }
                is DefaultViewStates.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PaymentMethodScreenDisplayPreview() {
    PaymentMethodScreen(DefaultViewStates.Display(
        PaymentMethodViewData("Payment Method №1")
    )) {}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PaymentMethodScreenLoadingPreview() {
    PaymentMethodScreen(DefaultViewStates.Loading(
        PaymentMethodViewData("Payment Method №1")
    )) {}
}