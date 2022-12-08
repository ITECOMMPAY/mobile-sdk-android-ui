package com.paymentpage.msdk.ui.presentation.main.screens.customerFields

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.presentation.main.sendCustomerFields
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
internal fun CustomerFieldsScreen(
    onCancel: () -> Unit,
    onBack: () -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val method = viewModel.lastState.currentMethod
    val customerFields = viewModel.lastState.customerFields
    var isCustomerFieldsValid by remember { mutableStateOf(method?.isCustomerFieldsValid ?: false) }


    BackHandler(true) { onBack() }

    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        title = getStringOverride("title_payment_additional_data"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                text = getStringOverride("title_payment_additional_data_disclaimer"),
                style = SDKTheme.typography.s14Normal
            )
            Spacer(modifier = Modifier.size(5.dp))
            CustomerFields(
                customerFieldValues = method?.customerFieldValues ?: emptyList(),
                customerFields = customerFields,
                additionalFields = LocalPaymentOptions.current.additionalFields,
                onCustomerFieldsChanged = { fields, isValid ->
                    isCustomerFieldsValid = isValid
                    method?.customerFieldValues = fields
                    method?.isCustomerFieldsValid = isValid

                }
            )
            Spacer(modifier = Modifier.size(22.dp))
            PayButton(
                payLabel = getStringOverride("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isCustomerFieldsValid
            ) {
                viewModel.sendCustomerFields(method?.customerFieldValues ?: emptyList())
            }
            Spacer(modifier = Modifier.size(5.dp))
        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = { onCancel() },
        onBack = { onBack() }
    )
}
