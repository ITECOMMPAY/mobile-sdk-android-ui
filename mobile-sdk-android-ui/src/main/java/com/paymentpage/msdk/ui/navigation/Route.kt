@file:Suppress("unused")

package com.paymentpage.msdk.ui.navigation


sealed class Route(
    private val route: String,
    private val key: String = ""
) {
    object Init : Route(route = "init")
    object Main : Route(route = "main")
    object CustomerFields : Route(route = "customerFields")
    object ClarificationFields : Route(route = "clarificationFields")
    object Loading : Route(route = "loading")
    object Result : Route(route = "result")

    override fun toString(): String {
        return when {
            key.isNotEmpty() -> "$route/{$key}"
            else -> route
        }
    }

    fun getPath() = toString()
}