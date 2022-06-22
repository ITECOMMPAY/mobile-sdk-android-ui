package com.ecommpay.msdk.ui.entry

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethod
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodIntents
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.views.SDKTopBar
import com.ecommpay.msdk.ui.views.ShimmerAnimation

@Composable
fun EntryScreen(
    state: ViewStates<EntryViewData>,
    intentListener: (intent: ViewIntents) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(SDKTheme.colors.backgroundPaymentMethods),
        content = {
            Column(
                modifier = Modifier
                    .padding(SDKTheme.dimensions.paddingDp20)
            ) {
                SDKTopBar(
                    title = state.viewData.topAppBarTitle,
                    arrowIcon = null
                )
                when (state) {
                    is DefaultViewStates.Loading -> {
                        ShimmerAnimation(
                            itemHeight = 20.dp,
                            itemWidth = 125.dp,
                            borderRadius = 4.dp
                        )
                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )
                        ShimmerAnimation(
                            itemHeight = 150.dp,
                            borderRadius = 12.dp
                        )
                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )
                        Row {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ShimmerAnimation(
                                    itemHeight = 50.dp,
                                    borderRadius = 6.dp,
                                )
                            }
                            Spacer(
                                modifier = Modifier.size(10.dp)
                            )
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ShimmerAnimation(
                                    itemHeight = 50.dp,
                                    borderRadius = 6.dp,
                                )
                            }
                        }
                    }
                    else -> {
                        Text(
                            text = state.viewData.paymentDetailsTitle,
                            style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.brand)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.size(10.dp)
                )
                PaymentMethodList(
                    paymentMethodList = state.viewData.paymentMethodList,
                    intentListener = intentListener
                )
            }
        }
    )
}

@Composable
private fun PaymentMethodList(
    paymentMethodList: List<ViewData>,
    intentListener: (intent: ItemPaymentMethodIntents) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
        paymentMethodList.forEach { itemPaymentMethodViewData ->
            when (itemPaymentMethodViewData) {
                is ShimmerViewData -> {
                    ShimmerAnimation(
                        itemHeight = 50.dp,
                        borderRadius = 6.dp
                    )
                }
                is ItemPaymentMethodViewData -> {
                    ItemPaymentMethod(
                        name = itemPaymentMethodViewData.name,
                        intentListener = intentListener,
                        iconUrl = itemPaymentMethodViewData.iconUrl)
                }
            }
            Spacer(
                modifier = Modifier.size(10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenDisplayPreview() {
    EntryScreen(DefaultViewStates.Display(EntryViewData.defaultViewData)) {}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenLoadingPreview() {
    EntryScreen(DefaultViewStates.Loading(EntryViewData.defaultViewData)) {}
}