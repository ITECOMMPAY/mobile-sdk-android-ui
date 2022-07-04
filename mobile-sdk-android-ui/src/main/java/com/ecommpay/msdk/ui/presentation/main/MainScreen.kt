package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.model.init.UIPaymentMethod
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.presentation.main.views.ExpandablePaymentDetails
import com.ecommpay.msdk.ui.presentation.main.views.ExpandablePaymentMethodItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.card.CardView
import com.ecommpay.msdk.ui.views.common.Scaffold


@Composable
internal fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navigator: Navigator,
    paymentMethods: List<UIPaymentMethod>,
    paymentOptions: PaymentOptions,
) {
    // val state by viewModel.state.collectAsState()
    Content(paymentMethods, paymentOptions)
}


@Composable
private fun Content(paymentMethods: List<UIPaymentMethod>, paymentOptions: PaymentOptions) {
    Scaffold(
        title = PaymentActivity.stringResourceManager.payment.methodsTitle
            ?: stringResource(R.string.payment_methods_label),
        notScrollableContent = {
            ExpandablePaymentDetails(paymentOptions = paymentOptions)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
            CardView(
                price = PaymentActivity.paymentOptions?.paymentAmount.amountToCoins(),
                currency = PaymentActivity.paymentOptions?.paymentCurrency?.uppercase() ?: "USD",
                vatIncludedTitle = stringResource(id = R.string.vat_included_label)
            )
            Spacer(
                modifier = Modifier.size(SDKTheme.dimensions.paddingDp15)
            )
        },
        scrollableContent = {
            PaymentMethodList(paymentMethods = paymentMethods)
        }
    )
}

@Composable
private fun PaymentMethodList(paymentMethods: List<UIPaymentMethod>) {
    var expandedPosition by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        paymentMethods.forEachIndexed { position, paymentMethod ->
            ExpandablePaymentMethodItem(
                position = position,
                name = paymentMethod.name,
                iconUrl = paymentMethod.iconUrl,
                onExpand = { expandPosition ->
                    expandedPosition = expandPosition
                },
                isExpanded = expandedPosition == position
            ) {
                Spacer(
                    modifier = Modifier // testing content
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PaymentMethods() {
    Content(
        paymentMethods = UIPaymentMethod.previewData(10),
        paymentOptions = PaymentOptions()
    )
}