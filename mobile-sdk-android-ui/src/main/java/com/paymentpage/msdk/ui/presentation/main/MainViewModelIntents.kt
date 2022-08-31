package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveRequest
import com.paymentpage.msdk.core.domain.interactors.pay.aps.ApsSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.NewCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.SavedCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePaySaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.restore.PaymentRestoreRequest
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.twoDigitYearToFourDigitYear

//sale with google pay
internal fun MainViewModel.saleGooglePay(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    merchantId: String,
    token: String,
    environment: GooglePayEnvironment
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = GooglePaySaleRequest(
        merchantId = merchantId,
        token = token,
        environment = environment
    )
    if (method.customerFieldValues.size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS)
        request.customerFields = method.customerFieldValues
    this.payInteractor.execute(request, this)
}

//sale with saved card
internal fun MainViewModel.saleSavedCard(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = SavedCardSaleRequest(cvv = method.cvv, accountId = method.accountId)
    if (method.customerFieldValues.size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS)
        request.customerFields = method.customerFieldValues
    this.payInteractor.execute(request, this)
}

internal fun MainViewModel.deleteSavedCard(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
) {
    sendEvent(MainScreenUiEvent.ShowDeleteCardLoading(isLoading = true))
    val request = CardRemoveRequest(id = method.accountId)
    this.cardRemoveInteractor.execute(request, this)
}

internal fun MainViewModel.showAps(
    method: UIPaymentMethod.UIApsPaymentMethod,
) {
    sendEvent(MainScreenUiEvent.ShowApsPage(apsMethod = method.paymentMethod))
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
}

//sale with aps
internal fun MainViewModel.saleAps(
    method: UIPaymentMethod.UIApsPaymentMethod,
) {
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = ApsSaleRequest(methodCode = method.paymentMethod.code)
    payInteractor.execute(request = request, callback = this)
}

//sale with new card
internal fun MainViewModel.saleCard(
    method: UIPaymentMethod.UICardPayPaymentMethod
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val expiry = SdkExpiry(method.expiry)
    val request = NewCardSaleRequest(
        cvv = method.cvv,
        pan = method.pan,
        year = expiry.year?.twoDigitYearToFourDigitYear() ?: 0,
        month = expiry.month ?: 0,
        cardHolder = method.cardHolder,
        saveCard = method.saveCard
    )
    if (method.customerFieldValues.size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS)
        request.customerFields = method.customerFieldValues
    payInteractor.execute(request, this)
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
internal fun MainViewModel.threeDSecureHandled() {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.threeDSecureHandled()
}

//restore payment if it received via init
internal fun MainViewModel.restorePayment(methodCode: String) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.execute(PaymentRestoreRequest(methodCode = methodCode), this)
}

internal fun MainViewModel.restoreAps(apsMethod: PaymentMethod) {
    sendEvent(
        MainScreenUiEvent.SetCurrentMethod(
            UIPaymentMethod.UIApsPaymentMethod(
                index = 0,
                title = apsMethod.name ?: apsMethod.code,
                paymentMethod = apsMethod
            )
        )
    )
    sendEvent(MainScreenUiEvent.ShowApsPage(apsMethod = apsMethod))
    payInteractor.execute(PaymentRestoreRequest(methodCode = apsMethod.code), this)
}

//set current method
internal fun MainViewModel.setCurrentMethod(method: UIPaymentMethod?) {
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
}

//set current method
internal fun MainViewModel.showError(errorResult: ErrorResult) {
    sendEvent(MainScreenUiEvent.ShowError(errorResult))
}



