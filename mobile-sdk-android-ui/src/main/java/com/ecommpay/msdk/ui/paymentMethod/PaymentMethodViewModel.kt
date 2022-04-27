package com.ecommpay.msdk.ui.paymentMethod

import com.ecommpay.msdk.ui.base.BaseViewModel
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewIntents
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

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
