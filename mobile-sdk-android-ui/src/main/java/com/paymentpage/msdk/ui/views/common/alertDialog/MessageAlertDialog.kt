package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.runtime.Composable

@Composable
internal fun MessageAlertDialog(
    message: @Composable (() -> Unit),
    onConfirmButtonClick: (() -> Unit),
    onDismissRequest: (() -> Unit)? = null,
    onDismissButtonClick: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    confirmButtonText: String,
) {
    SDKAlertDialog(
        message = message,
        onConfirmButtonClick = onConfirmButtonClick,
        confirmButtonText = confirmButtonText,
        onDismissButtonClick = onDismissButtonClick,
        dismissButtonText = dismissButtonText,
        onDismissRequest = onDismissRequest
    )
}