package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.threeDSecure.EcmpThreeDSecurePaymentInfo
import com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.payment.Payment


internal fun Payment.map(): EcmpThreeDSecurePaymentInfo =
    EcmpThreeDSecurePaymentInfo(
        reorder = reorder,
        preorderPurchase = preorderPurchase,
        preorderDate = preorderDate,
        challengeIndicator = challengeIndicator,
        challengeWindow = challengeWindow,
        giftCard = giftCard?.map()
    )