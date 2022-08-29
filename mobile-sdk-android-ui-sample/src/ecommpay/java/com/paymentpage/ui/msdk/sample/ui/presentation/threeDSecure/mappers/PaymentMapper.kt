package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecurePaymentInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.payment.Payment


internal fun Payment.map() : EcmpThreeDSecurePaymentInfo =
    EcmpThreeDSecurePaymentInfo(
        reorder = reorder,
        preorderPurchase = preorderPurchase,
        preorderDate = preorderDate,
        challengeIndicator = challengeIndicator,
        challengeWindow = challengeWindow,
        giftCard = giftCard?.map()
    )