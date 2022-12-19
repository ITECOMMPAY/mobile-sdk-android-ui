package com.ecommpay.ui.msdk.sample.domain.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePaymentInfo
import com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.payment.Payment


internal fun Payment.map(): ThreeDSecurePaymentInfo =
    ThreeDSecurePaymentInfo(
        reorder = reorder,
        preorderPurchase = preorderPurchase,
        preorderDate = preorderDate,
        challengeIndicator = challengeIndicator,
        challengeWindow = challengeWindow,
        giftCard = giftCard?.map()
    )