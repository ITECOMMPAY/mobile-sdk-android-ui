package com.ecommpay.ui.paymentMethod

import com.ecommpay.ui.base.ViewIntents

sealed class PaymentMethodViewIntents: ViewIntents {
    object MoveToPaymentMethodsListView : PaymentMethodViewIntents()
}