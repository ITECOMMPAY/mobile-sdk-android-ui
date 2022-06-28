package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.ecommpay.msdk.ui.views.common.SDKScaffold


@Composable
internal fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController,
    paymentMethods: List<UIPaymentMethod>,
) {
    // val state by viewModel.state.collectAsState()
    Content(paymentMethods)
}


@Composable
private fun Content(paymentMethods: List<UIPaymentMethod>) {
    SDKScaffold(
        title = PaymentActivity.stringResourceManager.payment.methodsTitle
            ?: stringResource(R.string.payment_methods_label)
    ) {
        Text(
            text = stringResource(R.string.payment_details_label),
            style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.brand),
            //modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        SDKCardView(
            price = PaymentActivity.paymentInfo?.paymentAmount.amountToCoins(),
            currency = PaymentActivity.paymentInfo?.paymentCurrency?.uppercase() ?: "USD",
            vatIncludedTitle = stringResource(id = R.string.vat_included_label)
        )
        Spacer(
            modifier = Modifier.size(SDKTheme.dimensions.paddingDp15)
        )
        PaymentMethodList(paymentMethods = paymentMethods)
    }
}

@Composable
private fun PaymentMethodList(paymentMethods: List<UIPaymentMethod>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
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
internal fun PaymentMethods() {
    Content(
        UIPaymentMethod.previewData(10)
    )
}