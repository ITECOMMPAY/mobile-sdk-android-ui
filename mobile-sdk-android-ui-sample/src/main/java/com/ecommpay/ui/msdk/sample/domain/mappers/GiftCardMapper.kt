package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureGiftCardInfo
import com.ecommpay.ui.msdk.sample.data.storage.entities.threeDSecure.payment.GiftCard

internal fun GiftCard.map(): EcmpThreeDSecureGiftCardInfo =
    EcmpThreeDSecureGiftCardInfo(
        amount = amount,
        currency = currency,
        count = count
    )