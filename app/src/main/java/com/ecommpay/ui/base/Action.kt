package com.ecommpay.ui.base

import androidx.compose.runtime.Composable

open class Action {
    private var isFirstRun = true

    //вызывает лямбду один раз
    @Composable
    fun Invoke(function: @Composable () -> Unit) {
        if (isFirstRun) {
            isFirstRun = false
            function.invoke()
        }
    }
}
