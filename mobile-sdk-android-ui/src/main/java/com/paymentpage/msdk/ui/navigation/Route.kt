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
    object AcsPage : Route(route = "acsPage")
    object ApsPage : Route(route = "apsPage")
    object Loading : Route(route = "loading")
    object SuccessResult : Route(route = "successResult")
    object DeclineResult : Route(route = "declineResult")

    override fun toString(): String {
        return when {
            key.isNotEmpty() -> "$route/{$key}"
            else -> route
        }
    }

    fun getPath() = toString()
}