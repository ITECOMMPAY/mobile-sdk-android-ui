package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.runtime.Composable

@Composable
internal fun ConfirmAlertDialog(
    title: @Composable (() -> Unit),
    message: @Composable (() -> Unit),
    onConfirmButtonClick: (() -> Unit),
    onDismissRequest: (() -> Unit)? = null,
    confirmButtonText: String
) {
    SDKAlertDialog(
        title = title,
        message = message,
        onConfirmButtonClick = onConfirmButtonClick,
        confirmButtonText = confirmButtonText,
        onDismissRequest = onDismissRequest
    )
}