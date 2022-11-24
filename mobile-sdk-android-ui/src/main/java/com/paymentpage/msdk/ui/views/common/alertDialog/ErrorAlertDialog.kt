package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.runtime.Composable

@Composable
internal fun ErrorAlertDialog(
    title: @Composable (() -> Unit),
    message: @Composable (() -> Unit),
    onConfirmButtonClick: (() -> Unit),
    onDismissRequest: (() -> Unit),
    onDismissButtonClick: (() -> Unit)? = onDismissRequest,
    confirmButtonText: String,
    dismissButtonText: String? = null,
) {
    SDKAlertDialog(
        title = title,
        message = message,
        onConfirmButtonClick = onConfirmButtonClick,
        confirmButtonText = confirmButtonText,
        onDismissRequest = onDismissRequest,
        dismissButtonText = dismissButtonText,
        onDismissButtonClick = onDismissButtonClick
    )
}