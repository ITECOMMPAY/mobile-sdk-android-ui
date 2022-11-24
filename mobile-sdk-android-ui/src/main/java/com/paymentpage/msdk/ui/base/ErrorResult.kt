package com.paymentpage.msdk.ui.base

import com.paymentpage.msdk.core.base.ErrorCode


internal data class ErrorResult(val code: ErrorCode, val message: String?) {
    override fun toString(): String {
        return "code: ${code.name}, message: $message"
    }
}