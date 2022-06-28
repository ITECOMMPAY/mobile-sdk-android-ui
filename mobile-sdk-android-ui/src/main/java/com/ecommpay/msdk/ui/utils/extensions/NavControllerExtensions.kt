package com.ecommpay.msdk.ui.utils.extensions

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.navigation.Route

internal inline fun <reified T> NavBackStackEntry.getData(key: String): T? {
    val data = arguments?.getString(key)

    return when {
        data != null -> PaymentActivity.gson.fromJson(data)
        else -> null
    }
}

internal inline fun <reified T> NavController.navigate(
    route: Route,
    data: T
) {
    navigate(
        route.toString().replace("{${route.key}}", Uri.encode(PaymentActivity.gson.toJson(data)))
    )
}
