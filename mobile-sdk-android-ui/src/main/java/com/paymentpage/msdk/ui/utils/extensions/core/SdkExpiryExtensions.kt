package com.paymentpage.msdk.ui.utils.extensions.core

internal fun Int?.twoDigitYearToFourDigitYear(): Int? =
    try {
        "20$this".toInt()
    } catch (e: NumberFormatException) {
        null
    }

internal fun Int?.fourDigitYearToTwoDigitYear(): Int? =
    try {
        "${this?.mod(100)}".toInt()
    } catch (e: NumberFormatException) {
        null
    }


internal fun Int?.oneDigitMonthToTwoDigitMonth(): String? =
    when (this) {
        in 1..9 -> "0$this"
        in 10..12 -> "$this"
        else -> null
    }