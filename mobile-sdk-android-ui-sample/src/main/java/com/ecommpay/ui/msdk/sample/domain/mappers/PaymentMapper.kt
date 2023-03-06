package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecurePaymentInfo
import com.ecommpay.ui.msdk.sample.data.storage.entities.threeDSecure.payment.Payment


internal fun Payment.map(): EcmpThreeDSecurePaymentInfo =
    EcmpThreeDSecurePaymentInfo(
        reorder = reorder,
        preorderPurchase = preorderPurchase,
        preorderDate = preorderDate,
        challengeIndicator = challengeIndicator,
        challengeWindow = challengeWindow,
        giftCard = giftCard?.map()
    )