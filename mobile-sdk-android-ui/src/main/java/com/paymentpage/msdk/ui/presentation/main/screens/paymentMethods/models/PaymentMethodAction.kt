package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models

import com.paymentpage.msdk.core.domain.entities.customer.CustomerField

internal sealed interface PaymentMethodAction {
    val method: UIPaymentMethod?

    data class PayWithNewCard(
        override val method: UIPaymentMethod.UICardPayPaymentMethod,
        val customerFields: List<CustomerField>
    ) : PaymentMethodAction

    data class PayWithSavedCard(
        override val method: UIPaymentMethod.UISavedCardPayPaymentMethod,
        val customerFields: List<CustomerField>
    ) : PaymentMethodAction

    data class PayWithGooglePay(
        override val method: UIPaymentMethod.UIGooglePayPaymentMethod,
        val token: String
    ) : PaymentMethodAction

    data class ShowAps(
        override val method: UIPaymentMethod.UIApsPaymentMethod
    ) : PaymentMethodAction

    data class ShowSbpQR(
        override val method: UIPaymentMethod.UISbpQrPaymentMethod
    ) : PaymentMethodAction

    data class ShowError(
        override val method: UIPaymentMethod? = null,
        val message: String
    ) : PaymentMethodAction
}
