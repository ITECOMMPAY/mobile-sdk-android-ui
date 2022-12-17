package com.ecommpay.ui.msdk.sample.domain.ui.base

import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents

fun BaseViewUC<*, *>.back() {
    pushExternalIntent(NavigationViewIntents.Back())
}