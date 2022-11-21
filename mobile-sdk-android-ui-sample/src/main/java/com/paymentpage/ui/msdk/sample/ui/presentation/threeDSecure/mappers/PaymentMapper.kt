package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePaymentInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.payment.Payment


internal fun Payment.map(): ThreeDSecurePaymentInfo =
    ThreeDSecurePaymentInfo(
        reorder = reorder,
        preorderPurchase = preorderPurchase,
        preorderDate = preorderDate,
        challengeIndicator = challengeIndicator,
        challengeWindow = challengeWindow,
        giftCard = giftCard?.map()
    )