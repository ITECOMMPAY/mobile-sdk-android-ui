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
    private var savedAccountIds: Set<Long> = emptySet()

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
        val isCurrentSelected = areSameMethods(state.value.currentMethod, method)
        setCurrentMethod(if (isCurrentSelected) null else method)
    }

    fun onPaymentActionClicked(method: UIPaymentMethod) {
        setCurrentMethod(method)
    }

    fun clearCurrentMethod() {
        setCurrentMethod(null)
    }

    fun setPaymentMethods(uiPaymentMethods: List<UIPaymentMethod>) {
        sendEvent(PaymentMethodsUiEvent.SetPaymentMethods(uiPaymentMethods))
        val visibleMethods = getVisiblePaymentMethods(uiPaymentMethods)
        val selectedMethod = resolveCurrentMethod(visibleMethods)
        sendEvent(PaymentMethodsUiEvent.SetCurrentMethod(selectedMethod))
        sendEvent(
            PaymentMethodsUiEvent.SetVisiblePaymentMethods(
                toListItems(
                    methods = visibleMethods,
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
        this.savedAccountIds = savedAccounts?.map { it.id }?.toSet() ?: emptySet()

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

    override fun onSuccess(result: Boolean) {
        val method = state.value.currentMethod
        if (method is UIPaymentMethod.UISavedCardPayPaymentMethod) {
            savedAccountIds = savedAccountIds - method.accountId
        }

        val filteredMethods = state.value.paymentMethods
            .filter { paymentMethod ->
                if (paymentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod) {
                    savedAccountIds.contains(paymentMethod.savedAccount.id)
                } else {
                    true
                }
            }
            .filter { !areSameMethods(it, method) }

        val newMethodList = when {
            isSaleWithToken -> filteredMethods.filterIsInstance<UIPaymentMethod.UISavedCardPayPaymentMethod>()
            actionType == SDKActionType.Tokenize -> filteredMethods.firstOrNull()?.let { listOf(it) }
                ?: emptyList()
            else -> filteredMethods
        }

        setPaymentMethods(newMethodList)
    }

    private fun updateVisiblePaymentMethods(currentMethod: UIPaymentMethod?) {
        val visibleMethods = getVisiblePaymentMethods(state.value.paymentMethods)
        sendEvent(
            PaymentMethodsUiEvent.SetVisiblePaymentMethods(
                toListItems(
                    methods = visibleMethods,
                    currentMethod = currentMethod
                )
            )
        )
    }

    private fun getVisiblePaymentMethods(methods: List<UIPaymentMethod>): List<UIPaymentMethod> = methods

    private fun resolveCurrentMethod(visibleMethods: List<UIPaymentMethod>): UIPaymentMethod? {
        if (visibleMethods.isEmpty()) return null

        val currentMethod = state.value.currentMethod
        val selectedMethod = visibleMethods.firstOrNull { areSameMethods(it, currentMethod) }
        if (selectedMethod != null) return selectedMethod

        return visibleMethods.firstOrNull { it !is UIPaymentMethod.UIGooglePayPaymentMethod }
    }

    private fun toListItems(
        methods: List<UIPaymentMethod>,
        currentMethod: UIPaymentMethod?
    ): List<UIPaymentMethodListItem> {
        return methods.map { method ->
            UIPaymentMethodListItem(
                method = method,
                isSelected = areSameMethods(method, currentMethod)
            )
        }
    }

    private fun areSameMethods(left: UIPaymentMethod?, right: UIPaymentMethod?): Boolean {
        if (left == null || right == null) return false
        if (left::class != right::class) return false

        return when {
            left is UIPaymentMethod.UISavedCardPayPaymentMethod &&
                    right is UIPaymentMethod.UISavedCardPayPaymentMethod -> left.accountId == right.accountId

            left is UIPaymentMethod.UIApsPaymentMethod &&
                    right is UIPaymentMethod.UIApsPaymentMethod -> left.paymentMethod.code == right.paymentMethod.code

            else -> left.index == right.index
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
                setState(oldState.copy(paymentMethods = event.paymentMethods))
            }

            is PaymentMethodsUiEvent.SetVisiblePaymentMethods -> {
                setState(oldState.copy(visiblePaymentMethods = event.paymentMethods))
            }
        }
    }
}
