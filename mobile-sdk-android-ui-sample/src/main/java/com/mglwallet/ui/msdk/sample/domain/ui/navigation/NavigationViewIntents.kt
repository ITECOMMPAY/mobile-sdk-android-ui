package com.mglwallet.ui.msdk.sample.domain.ui.navigation

import com.mglwallet.ui.msdk.sample.domain.ui.base.ViewIntents


sealed interface NavigationViewIntents : ViewIntents {

    data class Navigate(
        val to: NavRoutes,
        val back: NavRoutes? = null
    ) : NavigationViewIntents

    data class Back(
        val to: String? = null,
        val inclusive: Boolean = false
    ) : NavigationViewIntents

}
