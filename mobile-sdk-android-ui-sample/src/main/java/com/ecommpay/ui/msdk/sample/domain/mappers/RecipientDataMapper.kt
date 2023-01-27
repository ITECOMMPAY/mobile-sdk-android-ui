package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.ui.msdk.sample.domain.entities.RecipientData
import com.paymentpage.msdk.core.domain.entities.RecipientInfo

internal fun RecipientData.map(): RecipientInfo = RecipientInfo(
    walletOwner = walletOwner,
    walletId = walletId,
    country = country,
    pan = pan,
    cardHolder = cardHolder,
    address = address,
    city = city,
    stateCode = stateCode
)