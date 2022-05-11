package com.ecommpay.msdk.ui.bottomSheetScreens.result

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class ResultViewIntents : ViewIntents {
    object ClickDone : ResultViewIntents()
}
