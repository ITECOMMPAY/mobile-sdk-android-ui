package com.ecommpay.ui.msdk.sample.domain.ui.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewUseCaseContract<VI: ViewIntents, VS: ViewState>{
    val viewState: StateFlow<VS?>
    val viewAction: SharedFlow<ViewActions?>

    fun pushIntent(intent: VI)

    fun pushExternalIntent(intent: ViewIntents)

    fun clear()
}