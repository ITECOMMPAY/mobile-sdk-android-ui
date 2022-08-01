@file:Suppress("unused")

package com.paymentpage.ui.msdk.sample.ui.navigation

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewActions


abstract class NavRouts(val route:String): ViewActions {
    object Main : NavRouts("main")
    object AdditionalFields : NavRouts("additionalFields")
}