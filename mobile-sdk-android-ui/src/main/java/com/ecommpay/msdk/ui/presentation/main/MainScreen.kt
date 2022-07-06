package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethodType
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.ecommpay.msdk.ui.presentation.main.views.method.PaymentMethodItem
import com.ecommpay.msdk.ui.presentation.main.views.method.SavedCardItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.card.CardView
import com.ecommpay.msdk.ui.views.common.Scaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navigator: Navigator,
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
) {
    // val state by viewModel.state.collectAsState()
    Content(paymentMethods, paymentOptions, savedAccounts)
}


@Composable
private fun Content(
    paymentMethods: List<PaymentMethod>,
    paymentOptions: PaymentOptions,
    savedAccounts: List<SavedAccount>,
) {
    Scaffold(
        title = PaymentActivity.stringResourceManager.payment.methodsTitle
            ?: stringResource(R.string.payment_methods_label),
        notScrollableContent = {
            PaymentDetailsView(paymentOptions = paymentOptions)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
            CardView(
                price = PaymentActivity.paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = PaymentActivity.paymentOptions.paymentInfo?.paymentCurrency?.uppercase()
                    ?: "USD",
                vatIncludedTitle = stringResource(id = R.string.vat_included_label)
            )
            Spacer(
                modifier = Modifier.size(SDKTheme.dimensions.paddingDp15)
            )
        },
        scrollableContent = {
            PaymentMethodList(paymentMethods = paymentMethods, savedAccounts = savedAccounts)
        }
    )
}

@Composable
private fun PaymentMethodList(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
) {
    var expandedPosition by remember { mutableStateOf(0) }

    val googlePayMethod = paymentMethods.find { it.type == PaymentMethodType.GOOGLE_PAY }
    val cardPayMethod = paymentMethods.find { it.type == PaymentMethodType.CARD }
    var position = remember { 0 }

    Column(modifier = Modifier.fillMaxWidth()) {
        //saved accounts
        savedAccounts.forEach { savedAccount ->
            SavedCardItem(
                position = position,
                savedAccount = savedAccount,
                isExpand = expandedPosition == position,
            ) { expandPosition -> expandedPosition = expandPosition }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
            position += 1
        }

        //pay with new card
        if (cardPayMethod != null) {
            PaymentMethodItem(
                position = position,
                title = PaymentActivity.stringResourceManager.buttonAddNewCardLabel
                    ?: stringResource(id = R.string.card_payment_method_label),
                isExpand = expandedPosition == position,
                customerFields = cardPayMethod.customerFields
            ) { expandPosition -> expandedPosition = expandPosition }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PaymentMethods() {
    Content(
        paymentMethods = (0..5).map {
            PaymentMethod(
                code = "card",
                name = "card $it"
            )
        },
        paymentOptions = PaymentOptions(),
        savedAccounts = emptyList()
    )
}