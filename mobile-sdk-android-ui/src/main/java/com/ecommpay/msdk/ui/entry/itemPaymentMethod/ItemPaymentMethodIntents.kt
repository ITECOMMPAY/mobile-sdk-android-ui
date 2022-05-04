package com.ecommpay.msdk.ui.entry.itemPaymentMethod

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class ItemPaymentMethodIntents: ViewIntents {
    data class Click (val name: String): ItemPaymentMethodIntents()
}
