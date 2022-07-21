package com.paymentpage.msdk.ui.presentation.customerFields

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.Footer
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
internal fun CustomerFieldsScreen(
    navigator: Navigator,
    onCancel: () -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = viewModel.lastState.customerFields
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    val method = viewModel.lastState.currentMethod
    var customerFieldValues by remember { mutableStateOf<List<CustomerFieldValue>?>(null) }
    val additionalFields = LocalAdditionalFields.current
    var isCustomerFieldsValid by remember { mutableStateOf(false) }

    BackHandler(true) { navigator.navigateTo(Route.Main) }

    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_additional_data"),
        notScrollableContent = {
            PaymentDetailsView(paymentInfo = LocalPaymentInfo.current)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
        },
        scrollableContent = {
            CardView(
                logoImage = PaymentActivity.logoImage,
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                vatIncludedTitle = when (method?.paymentMethod?.isVatInfo) {
                    true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
                    else -> null
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            CustomerFields(
                visibleCustomerFields = visibleCustomerFields,
                additionalFields = LocalAdditionalFields.current,
                onCustomerFieldsChanged = { fields, isValid ->
                    customerFieldValues = fields
                    isCustomerFieldsValid = isValid
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
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
            Footer(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
                privacyPolicy = PaymentActivity
                    .stringResourceManager
                    .getLinkMessageByKey("footer_privacy_policy")
                    .annotatedString()
            )
        },
        onClose = { onCancel() },
        onBack = { navigator.navigateTo(Route.Main) }
    )
}
