package com.ecommpay.msdk.ui.navigation

import com.ecommpay.msdk.ui.base.ViewActions

sealed class NavigationViewActions: ViewActions() {
    class PaymentMethodsListScreen: NavigationViewActions()
    class PaymentMethodsListScreenToPaymentMethodScreen(val name: String): NavigationViewActions()
    class PaymentMethodScreenToPaymentMethodsListScreen: NavigationViewActions()
}
