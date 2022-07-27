package com.paymentpage.msdk.ui

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.payment.Payment

internal interface PaymentDelegate {
    fun onError(code: ErrorCode, message: String?)

    fun onCompleteWithSuccess(payment: Payment)

    fun onCompleteWithDecline(payment: Payment)

    fun onCancel()
}