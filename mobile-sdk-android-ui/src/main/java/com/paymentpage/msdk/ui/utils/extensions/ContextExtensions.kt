package com.paymentpage.msdk.ui.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings

internal fun Context.drawableResourceIdFromDrawableName(name: String): Int {
    return this.resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
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