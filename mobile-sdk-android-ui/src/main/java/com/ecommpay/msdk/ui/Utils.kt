package com.ecommpay.msdk.ui

import java.util.*

internal object Utils {
    fun getFormattedAmount(amount: Long): String {
        return String.format(Locale.US, "%.2f", amount / 100.0)
    }
}