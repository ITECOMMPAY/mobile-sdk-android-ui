package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.mappers

import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData

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
        schedule = schedule?.map { it.map() }
    )