package com.ecommpay.msdk.ui.entry.itemSaveCard

import com.ecommpay.msdk.ui.base.ViewIntents
import com.ecommpay.msdk.ui.entry.deleteDialog.DeleteDialogViewData

sealed class ItemSaveCardIntents: ViewIntents {
    data class Click(val id: Long): ItemSaveCardIntents()
    data class Delete(val id: Long): ItemSaveCardIntents()
}
