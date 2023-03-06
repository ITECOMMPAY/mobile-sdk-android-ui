package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.runtime.Composable

@Composable
internal fun ConfirmAlertDialog(
    title: String? = null,
    message: String,
    onConfirmButtonClick: (() -> Unit),
    onDismissRequest: (() -> Unit)? = null,
    confirmButtonText: String,
    brandColor: String? = null,
) {
    SDKAlertDialog(
        title = title,
        message = message,
        onConfirmButtonClick = onConfirmButtonClick,
        confirmButtonText = confirmButtonText,
        onDismissRequest = onDismissRequest,
        brandColor = brandColor
    )
}