package com.paymentpage.msdk.ui.utils.extensions

import com.paymentpage.msdk.core.validators.custom.DateValidator
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

private const val DEFAULT_CURRENCY_EXPONENT = 2
private const val MAX_CURRENCY_EXPONENT = 18

internal fun Long?.amountToCoins(currencyExponent: Int? = null): String {
    val exponent = currencyExponent
        ?.takeIf { it in 0..MAX_CURRENCY_EXPONENT }
        ?: DEFAULT_CURRENCY_EXPONENT

    return BigDecimal.valueOf(this ?: 0L)
        .movePointLeft(exponent)
        .setScale(exponent)
        .toPlainString()
}

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
    locale: Locale?,
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

internal fun String.toLanguageCode() = when (this.lowercase()) {
    "de" -> "GER"
    "en" -> "ENG"
    "es" -> "SPA"
    "fr" -> "FRA"
    "hu" -> "HUN"
    "it" -> "ITA"
    else -> this
}

internal fun String.toCurrencySign() = when (this) {
    "USD" -> "$"
    "EUR" -> "€"
    "GBP" -> "£"
    else -> this
}
