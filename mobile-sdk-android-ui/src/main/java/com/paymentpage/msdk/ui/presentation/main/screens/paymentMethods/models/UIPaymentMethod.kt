package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models


import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount


internal sealed class UIPaymentMethod(
    val index: Int,
    val title: String,
    val logoUrl: String?,
    val paymentMethod: PaymentMethod,
    var customerFieldValues: List<CustomerFieldValue> = emptyList(),
    var isCustomerFieldsValid: Boolean = false
) {
    abstract var pan: String?
    abstract var expiry: String?
    abstract var cvv: String?
    abstract var isValidCvv: Boolean
    abstract var isValidPan: Boolean
    abstract var isValidExpiry: Boolean

    class UIGooglePayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod,
    ) : UIPaymentMethod(index, title, paymentMethod.iconUrl, paymentMethod) {
        override var pan: String? = null
        override var expiry: String? = null
        override var cvv: String? = null
        override var isValidCvv: Boolean = true
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true
    }

    class UISavedCardPayPaymentMethod(
        index: Int,
        title: String,
        val savedAccount: SavedAccount,
        paymentMethod: PaymentMethod,
    ) : UIPaymentMethod(index, title, null, paymentMethod) {
        override var pan: String? = savedAccount.number
        override var expiry: String? = savedAccount.cardExpiry?.stringValue
        override var cvv: String? = null
        override var isValidCvv: Boolean = false
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true

        val accountId: Long = savedAccount.id
    }

    open class UICardPayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UIPaymentMethod(index, title, paymentMethod.iconUrl, paymentMethod) {
        override var pan: String? = null
        override var expiry: String? = null
        override var cvv: String? = null
        override var isValidCvv: Boolean = false
        override var isValidPan: Boolean = false
        override var isValidExpiry: Boolean = false

        var isValidCardHolder: Boolean = false
        var cardHolder: String = ""
        var saveCard: Boolean = false
    }

    class UIApsPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UIPaymentMethod(
        index = index,
        title = title,
        logoUrl = null,
        paymentMethod = paymentMethod,
    ) {
        override var pan: String? = null
        override var expiry: String? = null
        override var cvv: String? = null
        override var isValidCvv: Boolean = true
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true
    }
}