package com.ecommpay.msdk.ui.navigation

import com.ecommpay.msdk.ui.base.ViewActions

sealed class NavigationViewActions(open val navRoute: String): ViewActions() {
    class PaymentMethodsListScreenToPaymentMethodScreen(override val navRoute: String): NavigationViewActions(navRoute)
    class PaymentMethodScreenToPaymentMethodsListScreen(override val navRoute: String): NavigationViewActions(navRoute)
    class PaymentMethodsListScreenToEnterCVVBottomSheet(override val navRoute: String): NavigationViewActions(navRoute)
}
