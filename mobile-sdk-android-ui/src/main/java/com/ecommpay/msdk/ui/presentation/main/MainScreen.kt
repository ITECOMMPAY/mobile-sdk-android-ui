package com.ecommpay.msdk.ui.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentDelegate
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.PaymentMethodList
import com.ecommpay.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.utils.extensions.core.annotatedString
import com.ecommpay.msdk.ui.utils.viewModelFactory
import com.ecommpay.msdk.ui.views.common.CardView
import com.ecommpay.msdk.ui.views.common.Footer
import com.ecommpay.msdk.ui.views.common.Scaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = viewModel(
        factory = viewModelFactory {
            MainViewModel(
                payInteractor = PaymentActivity.msdkSession.getPayInteractor(),
                paymentInfo = PaymentActivity.paymentOptions.paymentInfo
                    ?: throw IllegalAccessException("PaymentInfo can not be null")
            )
        }
    ),
    navigator: Navigator,
    delegate: PaymentDelegate,
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions
) {
    // val state by viewModel.state.collectAsState()
    BackHandler(true) { delegate.onCancel() }
    Content(
        paymentMethods = paymentMethods,
        savedAccounts = savedAccounts,
        paymentOptions = paymentOptions,
        delegate = delegate
    )
}


@Composable
private fun Content(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
    delegate: PaymentDelegate
) {
    var selectedPaymentMethod by remember { mutableStateOf<UIPaymentMethod?>(null) }
    Scaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView(paymentOptions = paymentOptions)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
        },
        scrollableContent = {
            CardView(
                amount = PaymentActivity.paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = PaymentActivity.paymentOptions.paymentInfo?.paymentCurrency?.uppercase(),
                vatIncludedTitle = when (selectedPaymentMethod?.paymentMethod?.isVatInfo) {
                    true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
                    else -> null
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            PaymentMethodList(
                paymentMethods = paymentMethods,
                savedAccounts = savedAccounts,
                paymentOptions = paymentOptions,
            ) {
                selectedPaymentMethod = it
            }
        },
        footerContent = {
            Footer(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
                privacyPolicy = PaymentActivity
                    .stringResourceManager
                    .getLinkMessageByKey("footer_privacy_policy")
                    .annotatedString()
            )
        },
        onClose = { delegate.onCancel() }
    )
}

