@file:Suppress("unused")

package com.paymentpage.ui.msdk.sample.ui.navigation

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewActions


abstract class NavRoutes(val route: String) : ViewActions {
    object Main : NavRoutes("main")
    object AdditionalFields : NavRoutes("additionalFields")
    object Recurrent : NavRoutes("recurrent")
}