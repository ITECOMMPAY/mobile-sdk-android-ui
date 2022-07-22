package com.paymentpage.msdk.ui.utils.extensions.core

internal fun Int?.twoDigitYearToFourDigitYear(): Int? {
    return try {
        "20$this".toInt()
    } catch (e: NumberFormatException) {
        null
    }
}