package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models


import androidx.compose.runtime.Stable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount

@Stable
internal sealed class UIPaymentMethod(
    var customerFieldValues: List<CustomerFieldValue> = emptyList(),
    var isCustomerFieldsValid: Boolean = false
) {
    abstract val id: String
    abstract val index: Int
    abstract val title: String
    abstract val logoUrl: String?
    abstract val paymentMethod: PaymentMethod

    abstract var pan: String?
    abstract var expiry: String?
    abstract var cvv: String?
    abstract var isValidCvv: Boolean
    abstract var isValidPan: Boolean
    abstract var isValidExpiry: Boolean

    data class UIGooglePayPaymentMethod(
        override val index: Int,
        override val title: String,
        override val logoUrl: String?,
        override val paymentMethod: PaymentMethod,
    ) : UIPaymentMethod() {
        override val id: String = paymentMethod.code
        override var pan: String? = null
        override var expiry: String? = null
        override var cvv: String? = null
        override var isValidCvv: Boolean = true
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true
    }

    data class UISavedCardPayPaymentMethod(
        override val index: Int,
        override val title: String,
        override val logoUrl: String? = null,
        override val paymentMethod: PaymentMethod,
        val savedAccount: SavedAccount,
    ) : UIPaymentMethod() {
        override val id: String = savedAccount.id.toString()
        override var pan: String? = savedAccount.number
        override var expiry: String? = savedAccount.cardExpiry?.stringValue
        override var cvv: String? = null
        override var isValidCvv: Boolean = false
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true

        val accountId: Long = savedAccount.id
    }

    data class UICardPayPaymentMethod(
        override val index: Int,
        override val title: String,
        override val logoUrl: String?,
        override val paymentMethod: PaymentMethod,
    ) : UIPaymentMethod() {
        override val id: String = paymentMethod.code
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

    data class UIApsPaymentMethod(
        override val index: Int,
        override val title: String,
        override val logoUrl: String? = null,
        override val paymentMethod: PaymentMethod
    ) : UIPaymentMethod() {
        override val id: String = paymentMethod.code
        override var pan: String? = null
        override var expiry: String? = null
        override var cvv: String? = null
        override var isValidCvv: Boolean = true
        override var isValidPan: Boolean = true
        override var isValidExpiry: Boolean = true
    }
}