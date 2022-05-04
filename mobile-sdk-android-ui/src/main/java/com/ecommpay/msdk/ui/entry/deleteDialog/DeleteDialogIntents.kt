package com.ecommpay.msdk.ui.entry.deleteDialog

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class DeleteDialogIntents : ViewIntents {
    data class Cancel(val id: Long) : DeleteDialogIntents()
    data class Delete(val id: Long) : DeleteDialogIntents()
}