@file:Suppress("unused")

package com.ecommpay.msdk.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _sharedFlow = MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(navTarget: NavTarget) {
        _sharedFlow.tryEmit(navTarget)
    }

    enum class NavTarget {
        Main,
        Result
    }
}