package com.ecommpay.msdk.ui.entry

import com.ecommpay.msdk.ui.base.ViewData
import com.ecommpay.msdk.ui.entry.deleteDialog.DeleteDialogViewData
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCardIntents
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCardViewData

data class EntryViewData(
    val paymentMethodList: List<ItemPaymentMethodViewData>,
    val saveCardList: List<ItemSaveCardViewData>,
    val isEditableSavedCards: Boolean,
    val deleteDialogViewData: DeleteDialogViewData
) : ViewData {

    companion object {
        val defaultViewData = EntryViewData(
            paymentMethodList = listOf(
                ItemPaymentMethodViewData(
                    name = "card",
                    iconUrl = ""
                ),
                ItemPaymentMethodViewData(
                    name = "card2",
                    iconUrl = ""
                )
            ),
            saveCardList = listOf(
                ItemSaveCardViewData(
                    cardNumber = "****1234",
                    clickIntent = ItemSaveCardIntents.Click(-1),
                    deleteIntent = ItemSaveCardIntents.Delete(-1),
                    isShowDeleteIcon = false,
                    iconCardUrl = ""
                )
            ),
            isEditableSavedCards = true,
            deleteDialogViewData = DeleteDialogViewData.defaultViewData
        )
    }
}