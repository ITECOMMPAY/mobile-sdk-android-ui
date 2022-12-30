package com.paymentpage.msdk.ui.presentation.main.screens.result.views.success

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog

@Composable
internal fun ResultSuccessTokenizeContent(
    onClose: (Payment) -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.payment ?: throw IllegalStateException("Not found payment in State")

    ConfirmAlertDialog(
        message = { Text(text = getStringOverride(OverridesKeys.TITLE_RESULT_SUCCES_TOKENIZE)) },
        onConfirmButtonClick = { onClose(payment) },
        confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
        onDismissRequest = { onClose(payment) }
    )
}