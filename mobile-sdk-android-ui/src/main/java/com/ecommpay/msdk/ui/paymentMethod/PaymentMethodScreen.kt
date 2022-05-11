package com.ecommpay.msdk.ui.paymentMethod

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.theme.SDKTypography
import com.ecommpay.msdk.ui.views.SDKToolBar

@Composable
fun PaymentMethodScreen(
    state: ViewStates<PaymentMethodViewData>?,
    intentListener: (intent: PaymentMethodViewIntents) -> Unit,
) {
    BackHandler {
        intentListener(PaymentMethodViewIntents.MoveToPaymentMethodsListView)
    }
    Scaffold(
        topBar = {
            SDKToolBar {
                intentListener(PaymentMethodViewIntents.MoveToPaymentMethodsListView)
            }
        },
        content = {
            when (state) {
                is DefaultViewStates.Display -> {
                    Text(
                        text = state.viewData.title,
                        style = SDKTypography().body1,
                        modifier = Modifier.padding(it))
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