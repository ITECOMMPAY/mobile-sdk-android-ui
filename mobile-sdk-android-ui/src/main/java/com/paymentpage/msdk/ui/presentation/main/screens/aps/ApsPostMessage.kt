package com.paymentpage.msdk.ui.presentation.main.screens.aps

import org.json.JSONObject

internal enum class ApsPostMessageType {
    SUCCESS,
    FAIL,
    TRY_AGAIN,
    UNKNOWN;

    companion object {
        fun fromMessage(message: String): ApsPostMessageType = when (message) {
            "epframe.payment.success" -> SUCCESS
            "epframe.payment.fail" -> FAIL
            "epframe.payment.try_again" -> TRY_AGAIN
            else -> UNKNOWN
        }
    }
}

internal fun parseApsPostMessage(json: String): ApsPostMessageType {
    return try {
        val jsonObject = JSONObject(json)
        val message = jsonObject.optString("message")
        ApsPostMessageType.fromMessage(message)
    } catch (e: Exception) {
        ApsPostMessageType.UNKNOWN
    }
}
