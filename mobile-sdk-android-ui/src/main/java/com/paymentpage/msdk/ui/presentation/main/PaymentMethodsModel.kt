package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod

@Immutable
internal sealed class PaymentMethodsUiEvent : UiEvent {
    class SetCurrentMethod(val method: UIPaymentMethod?) : PaymentMethodsUiEvent()
}

@Immutable
internal data class PaymentMethodsState(
    val currentMethod: UIPaymentMethod? = null
) : UiState {
    companion object {
        fun initial() = PaymentMethodsState()
    }
}