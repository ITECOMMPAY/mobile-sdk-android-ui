package com.paymentpage.msdk.ui.utils.extensions

import android.os.Build
import com.paymentpage.msdk.core.validators.custom.DateValidator
import java.text.ParseException
import java.text.SimpleDateFormat
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

internal fun String.patternDateToPatternDate(
    inPattern: String,
    outPattern: String
): String? {
    val isDateValid = DateValidator().isValid(this)
    if (!isDateValid)
        return null
    val inputFormat = SimpleDateFormat(inPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outPattern, Locale.getDefault())
    return try {
        val parse = inputFormat.parse(this) ?: null
        if (parse != null)
            outputFormat.format(parse)
        else
            null
    } catch (ex: ParseException) {
        null
    }
}