package com.ecommpay.msdk.ui.entry.deleteDialog

import com.ecommpay.msdk.ui.base.ViewData

data class DeleteDialogViewData(
    val title: String,
    val number: String,
    val deleteIntent: DeleteDialogIntents.Delete,
    val cancelIntent: DeleteDialogIntents.Cancel,
) : ViewData {
    companion object {
        val defaultViewData = DeleteDialogViewData("",
            "",
            DeleteDialogIntents.Delete(-1),
            DeleteDialogIntents.Cancel(-1))
    }
}
