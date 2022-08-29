package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureGiftCardInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.payment.GiftCard

internal fun GiftCard.map() : EcmpThreeDSecureGiftCardInfo =
    EcmpThreeDSecureGiftCardInfo(
        amount = amount,
        currency = currency,
        count = count
    )