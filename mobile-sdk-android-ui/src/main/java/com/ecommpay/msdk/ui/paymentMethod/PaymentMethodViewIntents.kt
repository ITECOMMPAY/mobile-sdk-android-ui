package com.ecommpay.msdk.ui.paymentMethod

import com.ecommpay.msdk.ui.base.ViewIntents

sealed class PaymentMethodViewIntents: ViewIntents {
    object MoveToPaymentMethodsListView : PaymentMethodViewIntents()
}