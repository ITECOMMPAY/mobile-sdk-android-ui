package com.paymentpage.ui.msdk.sample.domain.ui.base

import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents

fun BaseViewUC<*, *>.back() {
    pushExternalIntent(NavigationViewIntents.Back())
}