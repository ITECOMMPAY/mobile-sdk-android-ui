package com.ecommpay.msdk.ui.navigation

import com.ecommpay.msdk.ui.base.ViewActions

sealed class NavigationViewActions(open val navRoute: String): ViewActions() {
    //Payment method
    class PaymentMethodsListScreenToPaymentMethodScreen(override val navRoute: String): NavigationViewActions(navRoute)
    class PaymentMethodScreenToPaymentMethodsListScreen(override val navRoute: String): NavigationViewActions(navRoute)
    //EnterCVV
    class PaymentMethodsListScreenToEnterCVVBottomSheet(override val navRoute: String): NavigationViewActions(navRoute)
    class EnterCVVBottomSheetToPaymentMethodsListScreen(override val navRoute: String): NavigationViewActions(navRoute)
    class EnterCVVBottomSheetToResultBottomSheet(override val navRoute: String): NavigationViewActions(navRoute)
}
