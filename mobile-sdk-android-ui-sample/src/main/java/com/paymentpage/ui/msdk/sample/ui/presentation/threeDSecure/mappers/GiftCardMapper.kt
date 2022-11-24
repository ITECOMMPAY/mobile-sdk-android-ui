package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureGiftCardInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.payment.GiftCard

internal fun GiftCard.map(): ThreeDSecureGiftCardInfo =
    ThreeDSecureGiftCardInfo(
        amount = amount,
        currency = currency,
        count = count
    )