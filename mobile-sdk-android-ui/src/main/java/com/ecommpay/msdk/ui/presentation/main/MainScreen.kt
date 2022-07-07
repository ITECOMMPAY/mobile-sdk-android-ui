package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.PaymentMethodList
import com.ecommpay.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.utils.viewModelFactory
import com.ecommpay.msdk.ui.views.common.CardView
import com.ecommpay.msdk.ui.views.common.SDKScaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = viewModel(
        factory = viewModelFactory {
            MainViewModel(
                payInteractor = PaymentActivity.msdkSession.getPayInteractor(),
                paymentInfo = PaymentActivity.paymentOptions.paymentInfo
                    ?: throw IllegalAccessException("Payment Info can not be null")
            )
        }
    ),
    navigator: Navigator,
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
) {
    // val state by viewModel.state.collectAsState()
    Content(paymentMethods, savedAccounts, paymentOptions)
}


@Composable
private fun Content(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
) {
    var selectedPaymentMethod by remember { mutableStateOf<UIPaymentMethod?>(null) }
    SDKScaffold(
        title = PaymentActivity.stringResourceManager.payment.methodsTitle
            ?: stringResource(R.string.payment_methods_label),
        notScrollableContent = {
            PaymentDetailsView(paymentOptions = paymentOptions)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
            CardView(
                amount = PaymentActivity.paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = PaymentActivity.paymentOptions.paymentInfo?.paymentCurrency?.uppercase()
                    ?: "",
                vatIncludedTitle = when (selectedPaymentMethod?.paymentMethod?.isVatInfo) {
                    true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
                    else -> null
                }
            )
            Spacer(
                modifier = Modifier.size(SDKTheme.dimensions.paddingDp15)
            )
        },
        scrollableContent = {
            PaymentMethodList(
                paymentMethods,
                savedAccounts,
                paymentOptions,
            ) {
                selectedPaymentMethod = it
            }
        },
    )
}

