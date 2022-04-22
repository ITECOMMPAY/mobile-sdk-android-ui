package com.ecommpay.ui.base

open class Action {
    var isFirstRun = true

    //вызывает лямбду один раз
    fun invoke(function: () -> Unit) {
        if (isFirstRun) {
            isFirstRun = false
            function.invoke()
        }
    }
}
