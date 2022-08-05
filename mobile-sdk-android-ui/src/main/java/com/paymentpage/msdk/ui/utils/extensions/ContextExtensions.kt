package com.paymentpage.msdk.ui.utils.extensions

import android.content.Context

internal fun Context.drawableResourceIdFromDrawableName(name: String): Int {
    return this.resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
}