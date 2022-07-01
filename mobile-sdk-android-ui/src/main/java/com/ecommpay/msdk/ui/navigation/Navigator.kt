package com.ecommpay.msdk.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class Navigator {

    private val _sharedFlow = MutableSharedFlow<Route>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(route: Route) {
        _sharedFlow.tryEmit(route)
    }
}