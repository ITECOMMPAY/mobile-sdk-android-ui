package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

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
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.showAps
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.PayButton

@Composable
internal fun ApsPayItem(
    method: UIPaymentMethod.UIApsPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current

    ExpandablePaymentMethodItem(
        method = method,
        isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.apsDefaultLogoResId),
        prefixNameResourceIcon = "aps",
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = getStringOverride(OverridesKeys.APS_PAYMENT_DISCLAIMER),
                color = SDKTheme.colors.primaryTextColor,
                style = SDKTheme.typography.s14Light,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(20.dp))
            PayButton(
                payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = true,
            ) {
                paymentMethodsViewModel.setCurrentMethod(method)
                mainViewModel.showAps(method = method)
            }
//            Spacer(modifier = Modifier.size(10.dp))
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = getStringOverride("aps_vat_disclaimer"),
//                color = SDKTheme.colors.primaryTextColor,
//                style = SDKTheme.typography.s12Light,
//                textAlign = TextAlign.Center
//            )
        }
    }
}