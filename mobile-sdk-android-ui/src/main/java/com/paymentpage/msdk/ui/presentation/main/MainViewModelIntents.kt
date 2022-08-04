package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.interactors.pay.aps.ApsSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.NewCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.SavedCardSaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePaySaleRequest
import com.paymentpage.msdk.core.domain.interactors.pay.restore.PaymentRestoreRequest
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.utils.extensions.core.twoDigitYearToFourDigitYear

//sale with google pay
internal fun MainViewModel.saleGooglePay(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    merchantId: String,
    token: String,
    environment: GooglePayEnvironment,
    allCustomerFields: List<CustomerField>,
    additionalFields: List<SDKAdditionalField>
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = GooglePaySaleRequest(
        merchantId = merchantId,
        token = token,
        environment = environment
    )
    request.customerFields = allCustomerFields.merge(
        changedFields = method.customerFieldValues,
        additionalFields = additionalFields
    )
    this.payInteractor.execute(request, this)
}

//sale with saved card
internal fun MainViewModel.saleSavedCard(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
    allCustomerFields: List<CustomerField>,
    additionalFields: List<SDKAdditionalField>
) {
    sendEvent(MainScreenUiEvent.ShowLoading)
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
    val request = SavedCardSaleRequest(cvv = method.cvv, accountId = method.accountId)
    request.customerFields = allCustomerFields.merge(
        changedFields = method.customerFieldValues,
        additionalFields = additionalFields
    )
    this.payInteractor.execute(request, this)
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
    val request = ApsSaleRequest(code = method.code)
    payInteractor.execute(request = request, callback = this)
}

//sale with new card
internal fun MainViewModel.saleCard(
    method: UIPaymentMethod.UICardPayPaymentMethod,
    allCustomerFields: List<CustomerField>,
    additionalFields: List<SDKAdditionalField>
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
    request.customerFields = allCustomerFields.merge(
        changedFields = method.customerFieldValues,
        additionalFields = additionalFields
    )
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
internal fun MainViewModel.setCurrentMethod(method: UIPaymentMethod?) {
    sendEvent(MainScreenUiEvent.SetCurrentMethod(method))
}



