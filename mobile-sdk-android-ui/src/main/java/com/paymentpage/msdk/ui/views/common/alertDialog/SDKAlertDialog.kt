package com.paymentpage.msdk.ui.views.common.alertDialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.customColor

@Composable
internal fun SDKAlertDialog(
    title: String? = null,
    message: String,
    onDismissButtonClick: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    onConfirmButtonClick: (() -> Unit),
    confirmButtonText: String,
    onDismissRequest: (() -> Unit)? = onDismissButtonClick,
    brandColor: String? = null,
) {
    AlertDialog(
        title = if (title != null) {
            {
                Text(
                    text = title,
                    color = SDKTheme.colors.neutral
                )
            }
        } else {
            null
        },
        text = {
            Text(
                text = message,
                color = SDKTheme.colors.neutral
            )
        },
        onDismissRequest = {
            if (onDismissRequest != null) {
                onDismissRequest()
            }
        },

        dismissButton = {
            if (onDismissButtonClick != null) {
                TextButton(onClick = onDismissButtonClick) {
                    Text(
                        text = dismissButtonText ?: "",
                        color = customColor(brandColor)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(
                    text = confirmButtonText,
                    color = customColor(brandColor)
                )
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = (onDismissRequest != null),
            dismissOnClickOutside = (onDismissRequest != null)
        ),
        backgroundColor = SDKTheme.colors.background
    )
}