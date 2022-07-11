package com.ecommpay.msdk.ui.utils.extensions.core

import android.content.Context
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethodType
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod

internal fun List<PaymentMethod>.mergeUIPaymentMethods(
    context: Context,
    savedAccounts: List<SavedAccount>? = null
): List<UIPaymentMethod> {
    val result = mutableListOf<UIPaymentMethod>()

    //val googlePayMethod = find { it.type == PaymentMethodType.GOOGLE_PAY }
    val cardPayMethod = find { it.type == PaymentMethodType.CARD }

    var position = 0

    //map payment methods for ui

    //get google pay host

//    googlePayMethod?.let {
//        uiPaymentMethods.add(
//            UIPaymentMethod.UIGooglePayPaymentMethod(
//                index = position,
//                title = PaymentActivity.stringResourceManager.getStringByKey("google_pay_host_title")
//                    ?: stringResource(id = R.string.google_pay_host_title),
//                paymentMethod = it
//            )
//        )
//        position += 1
//    }

    //get saved cards
    savedAccounts?.forEach {
        result.add(
            UIPaymentMethod.UISavedCardPayPaymentMethod(
                index = position,
                title = it.number ?: "****",
                paymentMethod = cardPayMethod,
                savedAccount = it,
            )
        )
        position += 1
    }

    //get card payment method
    cardPayMethod?.let {
        result.add(
            UIPaymentMethod.UICardPayPaymentMethod(
                index = position,
                title = PaymentActivity.stringResourceManager.getStringByKey("button_add_new_card")
                    ?: context.getString(R.string.card_payment_method_label),
                paymentMethod = it,
            )
        )
        position += 1
    }


    return result.toList()
}