package com.paymentpage.msdk.ui.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType

internal fun Context.drawableResourceIdFromDrawableName(name: String): Int {
    return this.resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
}

internal fun Context.stringResourceIdFromStringName(name: String, locale: String?): Int {
    if (locale != null) {
        val result = this.resources.getIdentifier(
            "${name}_${locale
                .replaceAfter("_", "")
                .replace("_", "")
                .lowercase()
            }",
            "string",
            this.packageName
        )
        return if (result <= 0)
            this.resources.getIdentifier(
                name,
                "string",
                this.packageName
            )
        else
            result
    }
    else {
        return this.resources.getIdentifier(
            name,
            "string",
            this.packageName
        )
    }
}

internal fun Context.paymentMethodLogoId(
    paymentMethodType: PaymentMethodType,
    paymentMethodName: String,
    isDarkTheme: Boolean,
): Int {
    val themePostfixes = mutableListOf("light")
    if (isDarkTheme) themePostfixes.add(index = 0, element = "dark")
    var id = 0
    themePostfixes.forEach {
        if (id > 0)
            return@forEach
        val name =
            "${paymentMethodType.name.lowercase()}_${paymentMethodName.lowercase()}_$it"
        id = drawableResourceIdFromDrawableName(name)
    }
    return id
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Should be called in the context of an Activity")
}

fun Context.navigateToAppSettings() {
    this.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", this.packageName, null)
        )
    )
}