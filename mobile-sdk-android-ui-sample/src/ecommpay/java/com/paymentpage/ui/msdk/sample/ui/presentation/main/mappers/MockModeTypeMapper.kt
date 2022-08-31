package com.paymentpage.ui.msdk.sample.ui.presentation.main.mappers

import com.ecommpay.msdk.ui.EcmpPaymentSDK
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.MockModeType

internal fun MockModeType.map() : EcmpPaymentSDK.EcmpMockModeType =
    when(this) {
        MockModeType.DISABLED -> EcmpPaymentSDK.EcmpMockModeType.DISABLED
        MockModeType.SUCCESS -> EcmpPaymentSDK.EcmpMockModeType.SUCCESS
        MockModeType.DECLINE -> EcmpPaymentSDK.EcmpMockModeType.DECLINE
    }