package com.ecommpay.msdk.ui.paymentMethodsList

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethod
import com.ecommpay.msdk.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.views.ToolBar

@Composable
fun PaymentMethodsListScreen(
    state: ViewStates<PaymentMethodsListViewData>,
    intentListener: (intent: PaymentMethodsListViewIntents) -> Unit,
) {
    Scaffold(
        topBar = { ToolBar(null) },
        content = {
            when (state) {
                is DefaultViewStates.Display -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        item {
                            state.viewData.paymentMethodList.forEach { itemPaymentMethodViewData ->
                                ItemPaymentMethod(
                                    name = itemPaymentMethodViewData.name,
                                    intentListener = intentListener)
                                Spacer(
                                    modifier = Modifier.size(10.dp))
                            }
                        }
                    }
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
    PaymentMethodsListScreen(DefaultViewStates.Display(PaymentMethodsListViewData(
        listOf(
            ItemPaymentMethodViewData(
                name = "card"),
            ItemPaymentMethodViewData(
                name = "card"))
    ))) {}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PaymentMethodScreenLoadingPreview() {
    PaymentMethodsListScreen(DefaultViewStates.Loading(PaymentMethodsListViewData(
        listOf(
            ItemPaymentMethodViewData(
                name = "card"),
            ItemPaymentMethodViewData(
                name = "card"))
    ))) {}
}