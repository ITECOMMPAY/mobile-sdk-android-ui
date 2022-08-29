package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.mappers

import com.ecommpay.msdk.ui.EcmpRecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData

internal fun RecurrentData.map() : EcmpRecurrentData =
    EcmpRecurrentData(
        register = register,
        type = type,
        expiryDay = expiryDay,
        expiryMonth = expiryMonth,
        expiryYear = expiryYear,
        period = period,
        time = time,
        startDate = startDate,
        scheduledPaymentID = scheduledPaymentID,
        amount = amount,
        schedule = schedule?.map { it.map() }
    )