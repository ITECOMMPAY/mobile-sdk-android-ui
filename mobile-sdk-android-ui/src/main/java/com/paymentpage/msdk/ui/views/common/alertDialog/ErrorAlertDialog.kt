package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.runtime.Composable

@Composable
internal fun ErrorAlertDialog(
    title: String,
    message: String,
    onConfirmButtonClick: (() -> Unit),
    onDismissRequest: (() -> Unit),
    onDismissButtonClick: (() -> Unit)? = onDismissRequest,
    confirmButtonText: String,
    dismissButtonText: String? = null,
    brandColor: String? = null,
) {
    SDKAlertDialog(
        title = title,
        message = message,
        onConfirmButtonClick = onConfirmButtonClick,
        confirmButtonText = confirmButtonText,
        onDismissRequest = onDismissRequest,
        dismissButtonText = dismissButtonText,
        onDismissButtonClick = onDismissButtonClick,
        brandColor = brandColor
    )
}