package com.ecommpay.ui.paymentMethod

import com.ecommpay.ui.base.BaseViewModel
import com.ecommpay.ui.base.DefaultViewStates
import com.ecommpay.ui.base.ViewIntents
import com.ecommpay.ui.base.ViewStates
import com.ecommpay.ui.navigation.NavigationViewActions

class PaymentMethodViewModel: BaseViewModel<PaymentMethodViewData, PaymentMethodViewIntents>() {
    override fun defaultViewData() = PaymentMethodViewData("Wow")

    fun paymentMethodEntryPoint(title: String) {
        updateState(DefaultViewStates.Display(PaymentMethodViewData(title)))
    }

    override fun obtainIntent(
        intent: PaymentMethodViewIntents,
        currentState: ViewStates<PaymentMethodViewData>
    ) {
       when (intent) {
           is PaymentMethodViewIntents.MoveToPaymentMethodsListView -> {
               launchAction(NavigationViewActions.PaymentMethodScreenToPaymentMethodsListScreen())
           }
       }
    }
}
