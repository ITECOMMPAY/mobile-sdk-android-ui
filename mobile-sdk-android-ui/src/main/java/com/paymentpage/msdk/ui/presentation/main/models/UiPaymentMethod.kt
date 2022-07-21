package com.paymentpage.msdk.ui.presentation.main.models


import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount


internal sealed class UiPaymentMethod(
    val index: Int,
    val title: String,
    val paymentMethod: PaymentMethod
) {
    class UIGooglePayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod,
    ) : UiPaymentMethod(index, title, paymentMethod)

    class UISavedCardPayPaymentMethod(
        index: Int,
        title: String,
        val savedAccount: SavedAccount,
        paymentMethod: PaymentMethod,
    ) : UiPaymentMethod(index, title, paymentMethod) {
        var cvv: String = ""
        val accountId: Long = savedAccount.id
    }

    class UICardPayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UiPaymentMethod(index, title, paymentMethod) {
        var cvv: String = ""
        var pan: String = ""
        var year: Int = 0
        var month: Int = 0
        var cardHolder: String = ""
        var saveCard: Boolean = false
    }
}