package com.paymentpage.msdk.ui.utils.extensions

import android.os.Build
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

internal fun Long?.amountToCoins() = String.format(Locale.US, "%.2f", (this ?: 0) / 100.0)

internal fun String.paymentDateToPatternDate(pattern: String): String {
    val ldt: LocalDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val currentZoneId: ZoneId = ZoneId.systemDefault()
    val currentZonedDateTime: ZonedDateTime = ldt.atZone(currentZoneId)
    val format: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return format.format(currentZonedDateTime)
}