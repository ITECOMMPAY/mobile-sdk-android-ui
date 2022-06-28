package com.ecommpay.msdk.ui.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, object : TypeToken<T>() {}.type)
}