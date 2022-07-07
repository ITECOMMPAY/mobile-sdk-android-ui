package com.ecommpay.msdk.ui.presentation.main.models

import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount


internal sealed class UIPaymentMethod(
    val index: Int,
    val title: String,
    val paymentMethod: PaymentMethod?
) {
    class UIGooglePayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod,
    ) : UIPaymentMethod(index, title, paymentMethod)

    class UISavedCardPayPaymentMethod(
        index: Int,
        title: String,
        val savedAccount: SavedAccount,
        paymentMethod: PaymentMethod?,
    ) : UIPaymentMethod(index, title, paymentMethod)

    class UICardPayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UIPaymentMethod(index, title, paymentMethod)
}