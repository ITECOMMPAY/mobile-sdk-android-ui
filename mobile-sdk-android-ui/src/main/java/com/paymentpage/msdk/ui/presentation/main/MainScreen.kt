package com.paymentpage.msdk.ui.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.views.PaymentMethodList
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString
import com.paymentpage.msdk.ui.utils.viewModelFactory
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.Footer
import com.paymentpage.msdk.ui.views.common.SDKScaffold


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    navigator: Navigator,
    delegate: PaymentDelegate,
) {
    val paymentInfo = LocalPaymentInfo.current
    val payInteractor = LocalMsdkSession.current.getPayInteractor()
    val viewModel: MainViewModel = viewModel(
        factory = viewModelFactory {
            MainViewModel(
                payInteractor = payInteractor,
                paymentInfo = paymentInfo
            )
        }
    )
    Content(delegate = delegate)
}


@Composable
private fun Content(delegate: PaymentDelegate) {
    var selectedPaymentMethod by remember { mutableStateOf<UIPaymentMethod?>(null) }
    val showDialogDismissDialog = remember { mutableStateOf(false) }
    BackHandler(true) {
        showDialogDismissDialog.value = true
    }
    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView(paymentInfo = LocalPaymentInfo.current)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
        },
        scrollableContent = {
            CardView(
                logoImage = PaymentActivity.logoImage,
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                vatIncludedTitle = when (selectedPaymentMethod?.paymentMethod?.isVatInfo) {
                    true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
                    else -> null
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            PaymentMethodList(
                paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList(),
                savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList(),
                additionalFields = LocalAdditionalFields.current
            ) { selectedPaymentMethod = it }
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
        onClose = { showDialogDismissDialog.value = true }
    )
    if (showDialogDismissDialog.value)
        AlertDialog(
            text = { Text(text = stringResource(R.string.payment_dismiss_confirm_message)) },
            onDismissRequest = { showDialogDismissDialog.value = false },
            confirmButton = {
                TextButton(onClick = { delegate.onCancel() })
                { Text(text = stringResource(R.string.ok_label)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialogDismissDialog.value = false })
                { Text(text = stringResource(R.string.cancel_label)) }
            }
        )
}