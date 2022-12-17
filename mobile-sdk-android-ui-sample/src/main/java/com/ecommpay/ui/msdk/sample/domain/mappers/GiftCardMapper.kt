package com.ecommpay.ui.msdk.sample.domain.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureGiftCardInfo
import com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.payment.GiftCard

internal fun GiftCard.map(): ThreeDSecureGiftCardInfo =
    ThreeDSecureGiftCardInfo(
        amount = amount,
        currency = currency,
        count = count
    )