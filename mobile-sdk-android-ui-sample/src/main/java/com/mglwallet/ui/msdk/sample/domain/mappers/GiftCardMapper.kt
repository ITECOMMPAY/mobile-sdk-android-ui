package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.threeDSecure.EcmpThreeDSecureGiftCardInfo
import com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.payment.GiftCard

internal fun GiftCard.map(): EcmpThreeDSecureGiftCardInfo =
    EcmpThreeDSecureGiftCardInfo(
        amount = amount,
        currency = currency,
        count = count
    )