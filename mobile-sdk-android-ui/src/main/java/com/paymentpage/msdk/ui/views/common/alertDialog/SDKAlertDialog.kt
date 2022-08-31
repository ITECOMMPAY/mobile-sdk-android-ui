package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogProperties
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun SDKAlertDialog(
    title: @Composable (() -> Unit)? = null,
    message: @Composable (() -> Unit),
    onDismissButtonClick: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    onConfirmButtonClick: (() -> Unit),
    confirmButtonText: String,
    onDismissRequest: (() -> Unit)? = onDismissButtonClick
) {
    AlertDialog(
        title = title,
        text = message,
        onDismissRequest = {
            if (onDismissRequest != null) {
                onDismissRequest()
            }
        },
        dismissButton = {
            TextButton(onClick = {
                if (onDismissButtonClick != null) {
                    onDismissButtonClick()
                }
            })
            {
                Text(
                    text = dismissButtonText ?: "",
                    color = SDKTheme.colors.brand
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick()
            })
            {
                Text(
                    text = confirmButtonText,
                    color = SDKTheme.colors.brand
                )
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = (onDismissRequest != null),
            dismissOnClickOutside = (onDismissRequest != null)
        )
    )
}