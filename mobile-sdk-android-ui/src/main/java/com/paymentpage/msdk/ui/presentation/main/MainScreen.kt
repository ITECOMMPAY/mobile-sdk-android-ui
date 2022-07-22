package com.paymentpage.msdk.ui.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.presentation.main.views.PaymentMethodList
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    navigator: Navigator,
    delegate: PaymentDelegate,
    onCancel: () -> Unit
) {
    Content(onCancel = onCancel)
}


@Composable
private fun Content(onCancel: () -> Unit) {
    BackHandler(true) { onCancel() }
    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView(paymentInfo = LocalPaymentInfo.current)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
        },
        scrollableContent = {
            CardView()
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            PaymentMethodList(
                paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList(),
                savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList(),
                additionalFields = LocalAdditionalFields.current,
            )
        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
                privacyPolicy = PaymentActivity
                    .stringResourceManager
                    .getLinkMessageByKey("footer_privacy_policy")
                    .annotatedString()
            )
        },
        onClose = { onCancel() }
    )
}

@Composable
private fun CardView() {
    val mainViewModel = LocalMainViewModel.current
    val currentMethod = mainViewModel.state.collectAsState().value.currentMethod
    CardView(
        logoImage = PaymentActivity.logoImage,
        amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
        currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
        vatIncludedTitle = when (currentMethod?.paymentMethod?.isVatInfo) {
            true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
            else -> null
        }
    )
}