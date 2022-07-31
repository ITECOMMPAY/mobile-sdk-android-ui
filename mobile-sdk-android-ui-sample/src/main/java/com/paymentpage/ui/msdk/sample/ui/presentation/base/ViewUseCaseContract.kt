package com.paymentpage.ui.msdk.sample.ui.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewUseCaseContract<VI: ViewIntents, VS: ViewState>{
    var useCaseScope: CoroutineScope
    val viewState: StateFlow<VS?>
    val viewAction: SharedFlow<ViewActions?>

    fun pushIntent(intent: VI)

}