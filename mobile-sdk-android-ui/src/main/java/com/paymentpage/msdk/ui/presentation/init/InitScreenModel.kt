package com.paymentpage.msdk.ui.presentation.init

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState

@Immutable
internal sealed class InitScreenUiEvent : UiEvent {
    object ShowLoading : InitScreenUiEvent()
    object InitLoaded : InitScreenUiEvent()
    class PaymentReceived(val payment: Payment) : InitScreenUiEvent()
    class ShowError(val error: ErrorResult) : InitScreenUiEvent()
}

@Immutable
internal data class InitScreenState(
    val isInitLoaded: Boolean = false,
    val error: ErrorResult? = null,
    val payment: Payment? = null
) : UiState {

    companion object {
        fun initial() = InitScreenState()
    }

    override fun toString(): String {
        return "isInitLoaded: $isInitLoaded, error: $error"
    }
}