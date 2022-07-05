package com.ecommpay.msdk.ui.utils.extensions.core

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