package com.mglwallet.ui.msdk.sample.domain.ui.base

import com.mglwallet.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents

fun BaseViewUC<*, *>.back() {
    pushExternalIntent(NavigationViewIntents.Back())
}