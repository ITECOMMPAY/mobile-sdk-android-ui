package com.ecommpay.ui.paymentMethodsList

import com.ecommpay.ui.base.ViewIntents

sealed class PaymentMethodsListViewIntents: ViewIntents {
    data class Click (val name: String): PaymentMethodsListViewIntents()
}