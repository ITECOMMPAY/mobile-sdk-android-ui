package com.paymentpage.ui.msdk.sample.utils

import androidx.compose.ui.graphics.Color

object HexToJetpackColor {
    fun getColor(colorString: String?): Color? {
        if (colorString.isNullOrEmpty())
            return null
        return try {
            Color(android.graphics.Color.parseColor(colorString))
        } catch (e: Exception) {
            null
        }
    }
}
