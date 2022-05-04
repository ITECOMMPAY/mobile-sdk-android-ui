package com.ecommpay.msdk.ui.paymentMethod

import android.os.Bundle
import com.ecommpay.msdk.ui.base.BaseViewModel
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewIntents
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

class PaymentMethodViewModel: BaseViewModel<PaymentMethodViewData>() {
    override fun defaultViewData() = PaymentMethodViewData("Wow")

    override fun entryPoint(arguments: Bundle?) {
        updateState(DefaultViewStates.Display(PaymentMethodViewData(arguments?.getString("title") ?: "")))
    }

    override fun obtainIntent(
        intent: ViewIntents,
        currentState: ViewStates<PaymentMethodViewData>
    ) {
       when (intent) {
           is PaymentMethodViewIntents.MoveToPaymentMethodsListView -> {
               launchAction(NavigationViewActions.PaymentMethodScreenToPaymentMethodsListScreen("entryScreen"))
           }
       }
    }
}
