package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import com.ecommpay.msdk.ui.base.ViewData

data class EnterCVVViewData(
    val cardUrlLogo: String,
    val cardNumber: String,
    val buttonPayText: String,
    val payClickIntent: EnterCVVViewIntents.PayClick
) : ViewData {
    companion object {
        val defaultViewData = EnterCVVViewData(cardUrlLogo = "", cardNumber = "", payClickIntent = EnterCVVViewIntents.PayClick(id = -1, cvv = ""), buttonPayText = "")
    }
}
