package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models


import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride


internal sealed class UIPaymentMethod(
    val index: Int,
    val title: String,
    val logoUrl: String?,
    val paymentMethod: PaymentMethod,
    var customerFieldValues: List<CustomerFieldValue> = emptyList(),
    var isCustomerFieldsValid: Boolean = false
) {
    class UIGooglePayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod,
    ) : UIPaymentMethod(index, title, paymentMethod.iconUrl, paymentMethod)
    class UISavedCardPayPaymentMethod(
        index: Int,
        title: String,
        val savedAccount: SavedAccount,
        paymentMethod: PaymentMethod,
    ) : UIPaymentMethod(index, title, null, paymentMethod) {
        var cvv: String = ""
        var isValidCvv: Boolean = false
        val accountId: Long = savedAccount.id
    }

    open class UICardPayPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UIPaymentMethod(index, title, paymentMethod.iconUrl, paymentMethod) {
        var cvv: String = ""
        var pan: String = ""
        var expiry: String = ""
        var cardHolder: String = ""
        var saveCard: Boolean = false

        var isValidCvv: Boolean = false
        var isValidPan: Boolean = false
        var isValidExpiry: Boolean = false
        var isValidCardHolder: Boolean = false
    }

    class UITokenizeCardPayPaymentMethod(
        index: Int = 0,
        title: String = getStringOverride(OverridesKeys.BUTTON_ADD_NEW_CARD),
        paymentMethod: PaymentMethod
    ) : UICardPayPaymentMethod(index, title, paymentMethod)

    class UIApsPaymentMethod(
        index: Int,
        title: String,
        paymentMethod: PaymentMethod
    ) : UIPaymentMethod(
        index = index,
        title = title,
        logoUrl = null,
        paymentMethod = paymentMethod,
    )
}