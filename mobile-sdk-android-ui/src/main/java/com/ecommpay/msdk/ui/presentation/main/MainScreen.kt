package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.presentation.main.data.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.PaymentMethodItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.card.SDKCardView
import com.ecommpay.msdk.ui.views.common.SDKFooter
import com.ecommpay.msdk.ui.views.common.SDKTopBar


@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController,
    paymentMethods: List<UIPaymentMethod>
) {
    // val state by viewModel.state.collectAsState()
    Content(paymentMethods)
}


@Composable
private fun Content(paymentMethods: List<UIPaymentMethod>) {
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
                PaymentMethodList(paymentMethods = paymentMethods)
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
private fun PaymentMethodList(paymentMethods: List<UIPaymentMethod>) {
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
            price = PaymentActivity.paymentInfo?.paymentAmount.amountToCoins(),
            currency = PaymentActivity.paymentInfo?.paymentCurrency?.uppercase() ?: "CUR",
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
@Preview(showBackground = true, showSystemUi = true)
fun PaymentMethods() {
    Content(
        listOf(UIPaymentMethod(name = "Card", iconUrl = "", code = "card"))
    )
}