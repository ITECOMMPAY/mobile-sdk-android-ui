package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.base.mvi.TimeMachine
import com.ecommpay.msdk.ui.presentation.main.views.PaymentMethodItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.Utils
import com.ecommpay.msdk.ui.views.SDKCardView
import com.ecommpay.msdk.ui.views.SDKFooter
import com.ecommpay.msdk.ui.views.SDKTopBar
import com.ecommpay.msdk.ui.views.ShimmerAnimation


@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    Content(state)
}


@Composable
private fun Content(state: MainScreenState) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(SDKTheme.colors.backgroundPaymentMethods),
        content = {
            Column(
                modifier = Modifier.padding(SDKTheme.dimensions.paddingDp20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SDKTopBar(
                    title = PaymentActivity.stringResourceManager.payment.methodsTitle
                        ?: "Payment Methods",
                    arrowIcon = null
                )
                when {
                    state.isLoading -> Loading()
                    state.data.isNotEmpty() -> PaymentMethodList(paymentMethods = state.data)
                }
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))

                SDKFooter(
                    iconLogo = R.drawable.sdk_logo,
                    poweredByText = "Powered by"
                )
            }
        }
    )
}

@Composable
private fun PaymentMethodList(paymentMethods: List<PaymentMethod>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Payment Details",
            style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.brand),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        SDKCardView(
            brandLogoUrl = "url",
            price = Utils.getFormattedAmount(PaymentActivity.paymentInfo.paymentAmount),
            currency = PaymentActivity.paymentInfo.paymentCurrency.uppercase(),
            totalPriceTitle = "Total price",
            vatIncluded = true,
            vatIncludedTitle = "(VAT included)"
        )
        Spacer(
            modifier = Modifier.size(SDKTheme.dimensions.paddingDp15)
        )
        paymentMethods.forEach { paymentMethod ->
            PaymentMethodItem(
                name = paymentMethod.name ?: "",
                iconUrl = paymentMethod.iconUrl
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        }
    }
}

@Composable
private fun Loading() {
    ShimmerAnimation(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    ShimmerAnimation(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimation(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimation(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 1..5) {
            ShimmerAnimation(
                itemHeight = SDKTheme.dimensions.paymentMethodItemHeight,
                borderRadius = 6.dp
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoadingPreview() {
    Content(state = MainScreenState.initial())
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PaymentMethods() {
    Content(
        state = MainScreenState(
            isLoading = false,
            data = listOf(PaymentMethod(code = "card", name = "Card Pay"))
        )
    )
}