package com.paymentpage.msdk.ui.presentation.init

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState

@Immutable
internal sealed class InitScreenUiEvent : UiEvent {
    object ShowLoading : InitScreenUiEvent()
    class InitLoaded(val paymentMethods: List<PaymentMethod>, val savedAccounts: List<SavedAccount>) : InitScreenUiEvent()
    class PaymentReceived(val payment: Payment) : InitScreenUiEvent()
    class ShowError(val error: ErrorResult) : InitScreenUiEvent()
}

@Immutable
internal data class InitScreenState(
    val paymentMethods: List<PaymentMethod>? = null,
    val savedAccounts: List<SavedAccount>? = null,
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