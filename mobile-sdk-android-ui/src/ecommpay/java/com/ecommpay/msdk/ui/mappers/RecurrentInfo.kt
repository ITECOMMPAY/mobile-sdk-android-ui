package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.RecurrentData
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfoSchedule

internal fun RecurrentData.map(): RecurrentInfo =
    RecurrentInfo(
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
        schedule = schedule?.map { RecurrentInfoSchedule(it.date, it.amount) }
    )