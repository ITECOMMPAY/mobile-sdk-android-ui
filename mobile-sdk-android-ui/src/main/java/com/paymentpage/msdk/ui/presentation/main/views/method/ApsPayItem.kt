package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.showAps
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.views.button.PayButton

@Composable
internal fun ApsPayItem(
    method: UIPaymentMethod.UIApsPaymentMethod,
) {
    val viewModel = LocalMainViewModel.current

    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.apsDefaultLogoResId),
        prefixNameResourceIcon = "aps"
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = PaymentActivity.stringResourceManager.getStringByKey("aps_payment_disclaimer"),
                color = SDKTheme.colors.primaryTextColor,
                style = SDKTheme.typography.s14Light,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(20.dp))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = true,
            ) {
                viewModel.showAps(
                    method = method,
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = PaymentActivity.stringResourceManager.getStringByKey("aps_vat_disclaimer"),
                color = SDKTheme.colors.primaryTextColor,
                style = SDKTheme.typography.s12Light,
                textAlign = TextAlign.Center
            )
        }
    }
}