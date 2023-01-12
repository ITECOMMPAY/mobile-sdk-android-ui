package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePage
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState

@Immutable
internal sealed interface MainScreenUiEvent : UiEvent {
    object ShowLoading : MainScreenUiEvent
    class ShowDeleteCardLoading(val isLoading: Boolean?) : MainScreenUiEvent
    class ShowError(val error: ErrorResult) : MainScreenUiEvent
    class ShowCustomerFields(val customerFields: List<CustomerField>) :
        MainScreenUiEvent
    object TryAgain : MainScreenUiEvent
    class ShowClarificationFields(val clarificationFields: List<ClarificationField>) :
        MainScreenUiEvent

    class ShowThreeDSecurePage(val threeDSecurePage: ThreeDSecurePage, val isCascading: Boolean) :
        MainScreenUiEvent

    object ShowSuccessPage : MainScreenUiEvent
    class ShowDeclinePage(val paymentMessage: String?, val isTryAgain: Boolean) :
        MainScreenUiEvent

    class ShowApsPage(val apsMethod: PaymentMethod?) : MainScreenUiEvent
}

@Immutable
internal data class MainScreenState(
    val isLoading: Boolean? = null,
    val isDeleteCardLoading: Boolean? = null,
    val isTryAgain: Boolean? = null,
    val customerFields: List<CustomerField> = emptyList(),
    val clarificationFields: List<ClarificationField> = emptyList(),
    val threeDSecurePageState: ThreeDSecurePageState? = null,
    val apsPageState: ApsPageState? = null,
    val finalPaymentState: FinalPaymentState? = null,
    val error: ErrorResult? = null
) : UiState

internal class ApsPageState(
    val apsMethod: PaymentMethod?
)

internal class ThreeDSecurePageState(
    val threeDSecurePage: ThreeDSecurePage? = null,
    val isCascading: Boolean = false
)

internal sealed class FinalPaymentState {
    class Decline(val paymentMessage: String?, val isTryAgain: Boolean) : FinalPaymentState()
    object Success : FinalPaymentState()
}
