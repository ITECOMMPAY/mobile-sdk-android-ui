package com.paymentpage.msdk.ui.presentation.main.screens.customerFields

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.sendCustomerFields
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.merge
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
    val customerFields = viewModel.lastState.customerFields
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    var customerFieldValues by remember { mutableStateOf<List<CustomerFieldValue>?>(null) }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(false) }

    BackHandler(true) { onBack() }

    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_additional_data"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            CustomerFields(
                visibleCustomerFields = visibleCustomerFields,
                additionalFields = LocalPaymentOptions.current.additionalFields,
                onCustomerFieldsChanged = { fields, isValid ->
                    customerFieldValues = fields
                    isCustomerFieldsValid = isValid
                }
            )
            Spacer(modifier = Modifier.size(22.dp))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isCustomerFieldsValid || visibleCustomerFields.isEmpty()
            ) {
                viewModel.sendCustomerFields(
                    customerFields.merge(
                        changedFields = customerFieldValues,
                        additionalFields = additionalFields
                    )
                )
            }

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
