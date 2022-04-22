package com.ecommpay.ui.main

import com.ecommpay.ui.base.ViewActions

sealed class MainViewActions: ViewActions() {
    class ShowToast(val message: String): MainViewActions()
}