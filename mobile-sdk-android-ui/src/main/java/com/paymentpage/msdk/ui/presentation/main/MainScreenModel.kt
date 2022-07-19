package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod

@Immutable
internal sealed class MainScreenUiEvent : UiEvent {
    class SetPaymentMethod(val method: UiPaymentMethod) : MainScreenUiEvent()
    object ShowLoading : MainScreenUiEvent()
    class ShowError(val error: ErrorResult) : MainScreenUiEvent()
    class ShowCustomerFields(val customerFields: List<CustomerField>) :
        MainScreenUiEvent()

    class ShowClarificationFields(val clarificationFields: List<ClarificationField>) :
        MainScreenUiEvent()
}

@Immutable
internal data class MainScreenState(
    val isLoading: Boolean? = null,
    val method: UiPaymentMethod? = null,
    val customerFields: List<CustomerField> = emptyList(),
    val clarificationFields: List<ClarificationField> = emptyList(),
    val error: ErrorResult? = null
) : UiState {

    companion object {
        fun initial() = MainScreenState()
    }
}
