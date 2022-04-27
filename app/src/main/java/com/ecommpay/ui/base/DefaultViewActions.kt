package com.ecommpay.ui.base


sealed class DefaultViewActions : ViewActions() {
    object Default : DefaultViewActions() //значение для инициализации
    class ShowMessage(val message: MessageUI) : DefaultViewActions()
}