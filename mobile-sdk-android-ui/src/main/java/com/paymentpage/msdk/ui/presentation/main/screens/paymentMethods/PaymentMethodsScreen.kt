package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun PaymentMethodsScreen(
    uiPaymentMethods: List<UIPaymentMethod>,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {

    val isSaleWithToken = LocalPaymentOptions.current.paymentInfo.token != null
    val filteredUIPaymentMethods = with(uiPaymentMethods) {
        if (isSaleWithToken)
            filterIsInstance<UIPaymentMethod.UISavedCardPayPaymentMethod>()
        else this
    }

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = getStringOverride(OverridesKeys.TITLE_PAYMENT_METHODS),
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            PaymentMethodList(uiPaymentMethods = filteredUIPaymentMethods)
            Spacer(modifier = Modifier.size(6.dp))
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
            Spacer(modifier = Modifier.size(25.dp))
        },
        onClose = onCancel
    )
}
