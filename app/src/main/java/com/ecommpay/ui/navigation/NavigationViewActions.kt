package com.ecommpay.ui.navigation

import com.ecommpay.ui.base.ViewActions

sealed class NavigationViewActions: ViewActions() {
    class PaymentMethodsListScreen: NavigationViewActions()
    class PaymentMethodsListScreenToPaymentMethodScreen(val name: String): NavigationViewActions()
    class PaymentMethodScreenToPaymentMethodsListScreen: NavigationViewActions()
}
