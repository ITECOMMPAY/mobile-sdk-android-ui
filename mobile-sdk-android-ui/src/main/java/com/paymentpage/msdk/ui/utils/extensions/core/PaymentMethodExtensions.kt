package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod

internal fun List<PaymentMethod>.mergeUIPaymentMethods(
    savedAccounts: List<SavedAccount>? = null
): List<UIPaymentMethod> {
    val result = mutableListOf<UIPaymentMethod>()

    val googlePayMethod = find { it.paymentMethodType == PaymentMethodType.GOOGLE_PAY }
    val cardPayMethod = find { it.paymentMethodType == PaymentMethodType.CARD }
    val apsPaymentMethods = filter { it.paymentMethodType == PaymentMethodType.APS }

    var position = 0
    //get google pay host
    googlePayMethod?.let {
        result.add(
            UIPaymentMethod.UIGooglePayPaymentMethod(
                index = position,
                title = googlePayMethod.name ?: getStringOverride("google_pay_host_title"),
                paymentMethod = it,
            )
        )
        position += 1
    }

    //get saved cards
    cardPayMethod?.let { paymentMethod ->
        savedAccounts?.forEach { account ->
            result.add(
                UIPaymentMethod.UISavedCardPayPaymentMethod(
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
            UIPaymentMethod.UICardPayPaymentMethod(
                index = position,
                title = getStringOverride("button_add_new_card"),
                paymentMethod = it,
            )
        )
        position += 1
    }

    apsPaymentMethods.forEach {
        result.add(
            UIPaymentMethod.UIApsPaymentMethod(
                index = position,
                title = it.name ?: getStringOverride(it.translations["title"] ?: ""),
                paymentMethod = it,
            )
        )
        position += 1
    }

    return result.toList()
}