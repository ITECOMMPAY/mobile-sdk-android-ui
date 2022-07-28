package com.paymentpage.msdk.ui.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.presentation.main.views.PaymentMethodList
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    navigator: Navigator,
    delegate: PaymentDelegate,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    Content(onCancel = onCancel, onError = onError)
}


@Composable
private fun Content(onCancel: () -> Unit, onError: (ErrorResult, Boolean) -> Unit) {
    BackHandler(true) { onCancel() }
    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            PaymentMethodList(onError = onError)
        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = onCancel
    )
}