package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod

@Immutable
internal sealed class MainScreenUiEvent : UiEvent {
    class ShowLoading(val method: UIPaymentMethod) : MainScreenUiEvent()
    class ShowError(val error: ErrorResult) : MainScreenUiEvent()
    class ShowCustomerFields(val customerFields: List<CustomerField>) :
        MainScreenUiEvent()
}

@Immutable
internal data class MainScreenState(
    val isLoading: Boolean = false,
    val data: List<PaymentMethod> = emptyList(),
    val customerFields: List<CustomerField> = emptyList(),
    val method: UIPaymentMethod? = null,
    val error: ErrorResult? = null
) : UiState {

    companion object {
        fun initial() = MainScreenState()
    }
}
