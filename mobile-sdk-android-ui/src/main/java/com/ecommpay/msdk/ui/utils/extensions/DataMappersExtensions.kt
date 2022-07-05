package com.ecommpay.msdk.ui.utils.extensions

import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.model.init.UIPaymentMethod

internal fun List<PaymentMethod>?.map() = this?.let { list ->
    list.map {
        UIPaymentMethod(
            code = it.code,
            name = it.name ?: "",
            iconUrl = it.iconUrl
        )
    }
} ?: emptyList()


internal fun PaymentOptions.map(): PaymentInfo {
    val paymentInfo = PaymentInfo(
        projectId,
        paymentId,
        paymentAmount,
        paymentCurrency,
        paymentDescription,
        customerId,
        regionCode,
        token,
        languageCode
    )
    paymentInfo.signature = signature
    return paymentInfo
}