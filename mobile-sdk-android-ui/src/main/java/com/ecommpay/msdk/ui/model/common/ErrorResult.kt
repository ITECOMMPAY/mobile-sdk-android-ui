package com.ecommpay.msdk.ui.model.common

import com.ecommpay.msdk.core.base.ErrorCode

internal data class ErrorResult(val code: ErrorCode, val message: String?) {
    override fun toString(): String {
        return "code: ${code.name}, message: $message"
    }
}