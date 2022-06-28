package com.ecommpay.msdk.ui.utils.extensions

import java.util.*

internal fun Long?.amountToCoins() = String.format(Locale.US, "%.2f", (this ?: 0) / 100.0)