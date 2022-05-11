package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class EnterCVVViewIntents: ViewIntents {
    data class PayClick(val id: Long, val cvv: String): EnterCVVViewIntents()
    object MoveToPaymentMethodsList: EnterCVVViewIntents()
}
