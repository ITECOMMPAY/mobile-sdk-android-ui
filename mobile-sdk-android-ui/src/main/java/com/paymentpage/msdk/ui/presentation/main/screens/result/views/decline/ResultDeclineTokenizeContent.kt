package com.paymentpage.msdk.ui.presentation.main.screens.result.views.decline

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog

@Composable
internal fun ResultDeclineTokenizeContent(
    onClose: (Payment) -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.payment ?: throw IllegalStateException("Not found payment in State")
    val paymentOptions = LocalPaymentOptions.current
    ConfirmAlertDialog(
        message = getStringOverride(OverridesKeys.TITLE_RESULT_ERROR_TOKENIZE),
        onConfirmButtonClick = { onClose(payment) },
        confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
        onDismissRequest = { onClose(payment) },
        brandColor = paymentOptions.brandColor
    )
}