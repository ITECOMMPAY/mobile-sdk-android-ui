package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class EnterCVVViewIntents: ViewIntents {
    object PayClick: EnterCVVViewIntents()
}
