package com.paymentpage.msdk.ui.utils.extensions.core

import java.lang.NumberFormatException
import java.util.*

internal fun twoDigitYearToFourDigitYear(twoDigitYear: Int?, yearPrefix: Int = Calendar.getInstance().get(Calendar.YEAR) / 100): Int? {
    return try {
        "$yearPrefix$twoDigitYear".toInt()
    } catch (e: NumberFormatException) {
        null
    }
}