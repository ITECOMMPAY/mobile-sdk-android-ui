package com.ecommpay.msdk.ui

import com.ecommpay.msdk.core.base.ErrorCode
import com.ecommpay.msdk.core.domain.entities.payment.Payment

internal interface PaymentDelegate {
    fun onError(code: ErrorCode, message: String?)

    fun onCompleteWithSuccess(payment: Payment)

    fun onCompleteWithFail(status: String?, payment: Payment)

    fun onCompleteWithDecline(payment: Payment)

    fun onCancel()
}