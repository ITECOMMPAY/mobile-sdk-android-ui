package com.ecommpay.msdk.ui.entry.itemSaveCard

data class ItemSaveCardViewData(
    val iconCardUrl: String?,
    val clickIntent: ItemSaveCardIntents.Click,
    val deleteIntent: ItemSaveCardIntents.Delete,
    val cardNumber: String,
    var isShowDeleteIcon: Boolean
)