package com.ecommpay.ui.base


sealed class DefaultViewActions : ViewActions() {
    object Default : DefaultViewActions() //значение для инициализации
}