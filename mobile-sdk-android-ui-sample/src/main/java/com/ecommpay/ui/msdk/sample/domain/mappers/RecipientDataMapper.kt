package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.EcmpRecipientInfo
import com.ecommpay.ui.msdk.sample.domain.entities.RecipientData

internal fun RecipientData.map(): EcmpRecipientInfo = EcmpRecipientInfo(
    walletOwner = walletOwner,
    walletId = walletId,
    country = country,
    pan = pan,
    cardHolder = cardHolder,
    address = address,
    city = city,
    stateCode = stateCode
)