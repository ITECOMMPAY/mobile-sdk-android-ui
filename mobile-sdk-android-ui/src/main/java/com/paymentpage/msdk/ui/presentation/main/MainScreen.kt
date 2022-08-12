package com.paymentpage.msdk.ui.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
    onCancel: () -> Unit
) {
    Content(onCancel = onCancel)
}


@Composable
private fun Content(onCancel: () -> Unit) {
    BackHandler(true) { onCancel() }
    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            PaymentMethodList()
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