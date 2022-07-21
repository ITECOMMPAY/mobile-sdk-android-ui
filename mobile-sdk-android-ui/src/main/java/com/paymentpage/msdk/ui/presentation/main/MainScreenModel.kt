package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod

@Immutable
internal sealed class MainScreenUiEvent : UiEvent {
    class SetPaymentMethod(val method: UiPaymentMethod) : MainScreenUiEvent()
    class SetPayment(val payment: Payment) : MainScreenUiEvent()
    object ShowLoading : MainScreenUiEvent()
    class ShowError(val error: ErrorResult) : MainScreenUiEvent()
    class ShowCustomerFields(val customerFields: List<CustomerField>) :
        MainScreenUiEvent()

    class ShowClarificationFields(val clarificationFields: List<ClarificationField>) :
        MainScreenUiEvent()

    class ShowAcsPage(val acsPage: AcsPage, val isCascading: Boolean) :
        MainScreenUiEvent()

    object ShowSuccessPage : MainScreenUiEvent()
    class ShowDeclinePage(val resultMessage: String?, val isTryAgain: Boolean) :
        MainScreenUiEvent()

}

@Immutable
internal data class MainScreenState(
    val isLoading: Boolean? = null,
    val method: UiPaymentMethod? = null,
    val payment: Payment? = null,
    val customerFields: List<CustomerField> = emptyList(),
    val clarificationFields: List<ClarificationField> = emptyList(),
    val acsPageState: AcsPageState? = null,
    val finalPaymentState: FinalPaymentState? = null,
    val error: ErrorResult? = null
) : UiState {

    companion object {
        fun initial() = MainScreenState()
    }
}

internal class AcsPageState(
    val acsPage: AcsPage? = null,
    val isCascading: Boolean = false
)

internal sealed class FinalPaymentState {
    class Decline(val resultMessage: String?, val isTryAgain: Boolean) : FinalPaymentState()
    object Success : FinalPaymentState()
}
