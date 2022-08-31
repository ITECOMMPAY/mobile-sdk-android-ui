package com.paymentpage.ui.msdk.sample.ui.presentation.main

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewActions

sealed interface MainViewActions: ViewActions {
    object Sale: MainViewActions
    data class ShowToast(val message: String) : MainViewActions
}