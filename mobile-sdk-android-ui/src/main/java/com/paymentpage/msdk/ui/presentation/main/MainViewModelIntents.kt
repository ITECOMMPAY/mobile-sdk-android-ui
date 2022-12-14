package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.domain.entities.CardDate
import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.interactors.pay.aps.ApsSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.CardSaleTokenizeRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.NewCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.SavedCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.tokenize.CardTokenizeRequest
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePaySaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.restore.PaymentRestoreRequest
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.twoDigitYearToFourDigitYear

//sale with google pay
internal fun MainViewModel.saleGooglePay(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    merchantId: String,
    token: String,
    environment: GooglePayEnvironment,
    needSendCustomerFields: Boolean
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    val request = GooglePaySaleRequest(
        merchantId = merchantId,
        token = token,
        environment = environment
    )
    if (needSendCustomerFields)
        request.customerFields = method.customerFieldValues
    this.payInteractor.sendRequest(request)
}

//sale with saved card
internal fun MainViewModel.saleSavedCard(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
    needSendCustomerFields: Boolean
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    val request = SavedCardSaleRequest(cvv = method.cvv, accountId = method.accountId)
    if (needSendCustomerFields)
        request.customerFields = method.customerFieldValues
    this.payInteractor.sendRequest(request)
}

//tokenize with saved card
internal fun MainViewModel.tokenizeSavedCard(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
    needSendCustomerFields: Boolean
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    val request = CardSaleTokenizeRequest(cvv = method.cvv)
    if (needSendCustomerFields)
        request.customerFields = method.customerFieldValues
    this.payInteractor.sendRequest(request)
}

internal fun MainViewModel.showAps(
    method: UIPaymentMethod.UIApsPaymentMethod,
) {
    sendEvent(MainScreenUiEvent.ShowApsPage(apsMethod = method.paymentMethod))
}

//sale with aps
internal fun MainViewModel.saleAps(
    method: UIPaymentMethod.UIApsPaymentMethod,
) {
    val request = ApsSaleRequest(methodCode = method.paymentMethod.code)
    payInteractor.sendRequest(request)
}

//sale with new card
internal fun MainViewModel.saleCard(
    method: UIPaymentMethod.UICardPayPaymentMethod,
    needSendCustomerFields: Boolean
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    val expiry = SdkExpiry(method.expiry)
    val request = NewCardSaleRequest(
        cvv = method.cvv,
        pan = method.pan,
        expiryDate = CardDate(
            month = expiry.month ?: 0,
            year = expiry.year?.twoDigitYearToFourDigitYear() ?: 0
        ),
        cardHolder = method.cardHolder,
        saveCard = method.saveCard
    )
    if (needSendCustomerFields)
        request.customerFields = method.customerFieldValues
    payInteractor.sendRequest(request)
}

internal fun MainViewModel.tokenizeCard(
    method: UIPaymentMethod.UITokenizeCardPayPaymentMethod,
    needSendCustomerFields: Boolean
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    val expiry = SdkExpiry(method.expiry)
    val request = CardTokenizeRequest(
        pan = method.pan,
        expiryDate = CardDate(
            month = expiry.month ?: 0,
            year = expiry.year?.twoDigitYearToFourDigitYear() ?: 0
        ),
        cardHolder = method.cardHolder
    )
    if (needSendCustomerFields)
        request.customerFields = method.customerFieldValues
    payInteractor.sendRequest(request)
}

//send customer fields to core interactor
internal fun MainViewModel.sendCustomerFields(customerFields: List<CustomerFieldValue>) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.sendCustomerFields(customerFields)
}

//send clarification fields to core interactor
internal fun MainViewModel.sendClarificationFields(clarificationFields: List<ClarificationFieldValue>) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.sendClarificationFields(clarificationFields)
}

//send when 3ds handled
internal fun MainViewModel.threeDSecureRedirectHandle(url: String) {
    payInteractor.threeDSecureRedirectHandle(url)
}

//restore payment if it received via init
internal fun MainViewModel.restorePayment() {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.sendRequest(PaymentRestoreRequest())
}

internal fun MainViewModel.restoreAps(apsMethod: PaymentMethod) {
    sendEvent(MainScreenUiEvent.ShowApsPage(apsMethod = apsMethod))
    payInteractor.sendRequest(PaymentRestoreRequest())
}

//set current method
internal fun MainViewModel.showError(errorResult: ErrorResult) {
    sendEvent(MainScreenUiEvent.ShowError(errorResult))
}

//try again
internal fun MainViewModel.tryAgain() {
    sendEvent(MainScreenUiEvent.TryAgain)
}


