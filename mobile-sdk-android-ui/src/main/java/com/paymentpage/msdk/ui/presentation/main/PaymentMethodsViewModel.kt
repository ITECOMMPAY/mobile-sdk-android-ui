package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveRequest
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.base.mvi.Reducer
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import com.paymentpage.msdk.ui.core.CardRemoveInteractorProxy
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethodListItem
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods
import kotlinx.coroutines.flow.StateFlow

internal class PaymentMethodsViewModel(
    val cardRemoveInteractor: CardRemoveInteractorProxy
) : BaseViewModel<PaymentMethodsState, PaymentMethodsUiEvent>(), CardRemoveDelegate {
    private var actionType: SDKActionType = SDKActionType.Sale
    private var isSaleWithToken: Boolean = false

    init {
        cardRemoveInteractor.addDelegate(this)
    }

    override fun onCleared() {
        super.onCleared()
        cardRemoveInteractor.removeDelegate(this)
    }

    override val reducer = PaymentMethodsReducer(PaymentMethodsState())

    override val state: StateFlow<PaymentMethodsState>
        get() = reducer.state

    override val timeMachine: TimeMachine<PaymentMethodsState>
        get() = reducer.timeMachine

    fun setCurrentMethod(method: UIPaymentMethod?) {
        sendEvent(PaymentMethodsUiEvent.SetCurrentMethod(method))
        updateVisiblePaymentMethods(currentMethod = method)
    }

    fun onPaymentMethodClick(method: UIPaymentMethod) {
        val isCurrentSelected = state.value.currentMethod?.id == method.id

        setCurrentMethod(method.takeUnless { isCurrentSelected })
    }

    fun onPaymentActionClicked(method: UIPaymentMethod) {
        setCurrentMethod(method)
    }

    fun resetCurrentMethod() {
        setCurrentMethod(null)
    }

    fun setPaymentMethods(uiPaymentMethods: List<UIPaymentMethod>) {
        val selectedMethod = resolveCurrentMethod(uiPaymentMethods)
        sendEvent(PaymentMethodsUiEvent.SetCurrentMethod(selectedMethod))
        sendEvent(
            PaymentMethodsUiEvent.SetPaymentMethods(
                toListItems(
                    methods = uiPaymentMethods,
                    currentMethod = selectedMethod
                )
            )
        )
    }

    fun updatePaymentMethods(
        actionType: SDKActionType,
        paymentMethods: List<PaymentMethod>?,
        savedAccounts: List<SavedAccount>?,
        isSaleWithToken: Boolean,
    ) {
        this.actionType = actionType
        this.isSaleWithToken = isSaleWithToken

        val methods = paymentMethods?.mergeUIPaymentMethods(
            actionType = actionType,
            savedAccounts = savedAccounts
        ) ?: emptyList()

        setPaymentMethods(methods)
    }

    fun deleteSavedCard(method: UIPaymentMethod.UISavedCardPayPaymentMethod) {
        val request = CardRemoveRequest(id = method.accountId)
        this.cardRemoveInteractor.sendRequest(request = request)
    }

    override fun onError(code: ErrorCode, message: String) {}

    override fun onStartingRemove() {}

    // From card remove delegate
    override fun onSuccess(result: Boolean) {
        val deletedAccountId = (state.value.currentMethod as? UIPaymentMethod.UISavedCardPayPaymentMethod)?.accountId

        val filteredMethods = state.value.visiblePaymentMethods
            .filter { item ->
                item.method !is UIPaymentMethod.UISavedCardPayPaymentMethod ||
                    item.method.accountId != deletedAccountId
            }
            .map { it.method }

        val newMethodList = when {
            isSaleWithToken -> filteredMethods.filterIsInstance<UIPaymentMethod.UISavedCardPayPaymentMethod>()
            actionType == SDKActionType.Tokenize -> filteredMethods.take(1)
            else -> filteredMethods
        }

        setPaymentMethods(newMethodList)
    }

    private fun updateVisiblePaymentMethods(currentMethod: UIPaymentMethod?) {
        val visibleMethods = state.value.visiblePaymentMethods.map { it.method }

        sendEvent(
            PaymentMethodsUiEvent.SetPaymentMethods(
                toListItems(
                    methods = visibleMethods,
                    currentMethod = currentMethod
                )
            )
        )
    }

    private fun resolveCurrentMethod(visibleMethods: List<UIPaymentMethod>): UIPaymentMethod? {
        if (visibleMethods.isEmpty()) return null

        val currentMethod = state.value.currentMethod
        val selectedMethod = visibleMethods.firstOrNull { it.id == currentMethod?.id }
        if (selectedMethod != null) return selectedMethod

        return visibleMethods.firstOrNull { it !is UIPaymentMethod.UIGooglePayPaymentMethod }
    }

    private fun toListItems(
        methods: List<UIPaymentMethod>,
        currentMethod: UIPaymentMethod?,
    ): List<UIPaymentMethodListItem> {
        return methods.map { method ->
            UIPaymentMethodListItem(
                method = method,
                isSelected = method.id == currentMethod?.id
            )
        }
    }

}


internal class PaymentMethodsReducer(initial: PaymentMethodsState) :
    Reducer<PaymentMethodsState, PaymentMethodsUiEvent>(initial) {
    override fun reduce(oldState: PaymentMethodsState, event: PaymentMethodsUiEvent) {
        when (event) {
            is PaymentMethodsUiEvent.SetCurrentMethod -> {
                setState(oldState.copy(currentMethod = event.method))
            }

            is PaymentMethodsUiEvent.SetPaymentMethods -> {
                setState(oldState.copy(visiblePaymentMethods = event.paymentMethods))
            }
        }
    }
}
