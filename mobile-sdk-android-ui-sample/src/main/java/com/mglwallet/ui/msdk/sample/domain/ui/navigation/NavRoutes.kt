package com.mglwallet.ui.msdk.sample.domain.ui.navigation


sealed class NavRoutes(
    open val name: String,
) {
    object Exit : NavRoutes("exit")
}

sealed class MainHostScreens(
    override val name: String,
) : NavRoutes(name) {
    object MainScreen : MainHostScreens("main")
    object AdditionalFields : MainHostScreens("additionalFields")
    object ThreeDSecure : MainHostScreens("threeDSecure")
    object Recurrent : MainHostScreens("recurrent")
    object Recipient : MainHostScreens("recipient")
}
