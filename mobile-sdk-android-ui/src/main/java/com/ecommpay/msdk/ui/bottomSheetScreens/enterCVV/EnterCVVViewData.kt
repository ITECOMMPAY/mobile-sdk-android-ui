package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import com.ecommpay.msdk.ui.base.ViewData

data class EnterCVVViewData(
    val id: String
) : ViewData {
    companion object {
        val defaultViewData = EnterCVVViewData("-1")
    }
}
