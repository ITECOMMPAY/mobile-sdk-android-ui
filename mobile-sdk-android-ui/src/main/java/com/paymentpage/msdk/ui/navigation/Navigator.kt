package com.paymentpage.msdk.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _sharedFlow = MutableSharedFlow<Route>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    private var _lastRoute: Route? = null
    val lastRoute: Route?
        get() = _lastRoute

    fun navigateTo(route: Route) {
//        if (route == _lastRoute)
//            return
        _lastRoute = route
        _sharedFlow.tryEmit(route)
    }

}