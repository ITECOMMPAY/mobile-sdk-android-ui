package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.EcmpRecipientInfo
import com.mglwallet.ui.msdk.sample.domain.entities.RecipientData

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