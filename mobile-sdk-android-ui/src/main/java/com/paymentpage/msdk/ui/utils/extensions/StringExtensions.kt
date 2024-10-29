package com.paymentpage.msdk.ui.utils.extensions

import com.paymentpage.msdk.core.validators.custom.DateValidator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

internal fun Long?.amountToCoins() = String.format(Locale.US, "%.2f", (this ?: 0) / 100.0)

internal fun String.paymentDateToPatternDate(pattern: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val outputFormat = SimpleDateFormat(pattern, Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}


internal fun String.patternDateToPatternDate(
    inPattern: String,
    outPattern: String,
    locale: Locale?
): String? {
    val isDateValid = DateValidator().isValid(this)
    if (!isDateValid)
        return null
    val inputFormat = SimpleDateFormat(inPattern, locale ?: Locale.getDefault())
    val outputFormat = SimpleDateFormat(outPattern, locale ?: Locale.getDefault())
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