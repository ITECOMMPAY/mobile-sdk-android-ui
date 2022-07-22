package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.NewCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.SavedCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.restore.PaymentRestoreRequest
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod


//sale with saved card
internal fun MainViewModel.saleSavedCard(
    method: UiPaymentMethod.UISavedCardPayPaymentMethod,
    customerFields: List<CustomerFieldValue> = emptyList()
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = SavedCardSaleRequest(cvv = method.cvv, accountId = method.accountId)
    request.customerFields = customerFields
    this.payInteractor.execute(request, this)
}

//sale with new card
internal fun MainViewModel.saleCard(
    method: UiPaymentMethod.UICardPayPaymentMethod,
    customerFields: List<CustomerFieldValue> = emptyList()
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val expiry = SdkExpiry(method.expiry)
    val request = NewCardSaleRequest(
        cvv = method.cvv,
        pan = method.pan,
        year = expiry.year ?: 0,
        month = expiry.month ?: 0,
        cardHolder = method.cardHolder,
        saveCard = method.saveCard
    )
    request.customerFields = customerFields
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
internal fun MainViewModel.restorePayment() {
    sendEvent(MainScreenUiEvent.ShowLoading)
    payInteractor.execute(PaymentRestoreRequest(), this)
}

//set current method
internal fun MainViewModel.setCurrentMethod(method: UiPaymentMethod?) {
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
}

//reset state by default
internal fun MainViewModel.reset() {
    sendEvent(MainScreenUiEvent.Reset)
}




