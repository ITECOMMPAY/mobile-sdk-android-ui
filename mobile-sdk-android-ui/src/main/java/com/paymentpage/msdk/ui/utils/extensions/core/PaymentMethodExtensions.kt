package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod

internal fun List<PaymentMethod>.mergeUIPaymentMethods(
    savedAccounts: List<SavedAccount>? = null
): List<UiPaymentMethod> {
    val result = mutableListOf<UiPaymentMethod>()

    val googlePayMethod = find { it.type == PaymentMethodType.GOOGLE_PAY }
    val cardPayMethod = find { it.type == PaymentMethodType.CARD }

    var position = 0
    //get google pay host
    googlePayMethod?.let {
        result.add(
            UiPaymentMethod.UIGooglePayPaymentMethod(
                index = position,
                title = PaymentActivity.stringResourceManager.getStringByKey("google_pay_host_title"),
                paymentMethod = it
            )
        )
        position += 1
    }

    //get saved cards
    cardPayMethod?.let { paymentMethod ->
        savedAccounts?.forEach { account ->
            result.add(
                UiPaymentMethod.UISavedCardPayPaymentMethod(
                    index = position,
                    title = account.number ?: "****",
                    paymentMethod = paymentMethod,
                    savedAccount = account,
                )
            )
            position += 1
        }
    }

    //get card payment method
    cardPayMethod?.let {
        result.add(
            UiPaymentMethod.UICardPayPaymentMethod(
                index = position,
                title = PaymentActivity.stringResourceManager.getStringByKey("button_add_new_card"),
                paymentMethod = it,
            )
        )
        position += 1
    }


    return result.toList()
}