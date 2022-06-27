@file:Suppress("unused")

package com.ecommpay.msdk.ui.navigation


sealed class Route(
    private val route: String,
    val key: String = ""
) {
    object Init : Route(route = "main")
    object Main : Route(route = "main", key = "payment")
    object Result : Route(route = "result", key = "payment")

    override fun toString(): String {
        return when {
            key.isNotEmpty() -> "$route/{$key}"
            else -> route
        }
    }
}