package com.ecommpay.msdk.ui.paymentMethodsList

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class PaymentMethodsListViewIntents: ViewIntents {
    data class Click (val name: String): PaymentMethodsListViewIntents()
}