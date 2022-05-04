package com.ecommpay.msdk.ui.entry.deleteDialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ecommpay.msdk.ui.base.ViewIntents

@Composable
fun DeleteDialog(
    dialogViewData: DeleteDialogViewData,
    intentListener: (intent: ViewIntents) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            intentListener(dialogViewData.cancelIntent)
        },
        title = {
            Text(
                text = dialogViewData.title
            )
        },
        confirmButton = {
            Button(onClick = { intentListener(dialogViewData.deleteIntent) }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(onClick = {
                intentListener(dialogViewData.cancelIntent)
            }) {
                Text(text = "Cancel")
            }
        })
}
