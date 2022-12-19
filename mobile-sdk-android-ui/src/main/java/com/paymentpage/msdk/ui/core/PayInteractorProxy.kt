package com.paymentpage.msdk.ui.core

import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.interactors.pay.PayDelegate
import com.paymentpage.msdk.core.domain.interactors.pay.PayInteractor
import com.paymentpage.msdk.core.domain.interactors.pay.PayRequest

internal interface PayInteractorProxy {
    var delegate: PayDelegate?
    fun sendRequest(request: PayRequest)
    fun cancel()
    fun threeDSecureHandled()
    fun sendClarificationFields(fields: List<ClarificationFieldValue>)
    fun sendCustomerFields(fields: List<CustomerFieldValue>)
}

internal class PayInteractorProxyImpl(
    private val interactor: PayInteractor
) : PayInteractorProxy {

    override var delegate: PayDelegate? = null

    override fun sendRequest(request: PayRequest) {
        interactor.execute(request, delegate)
    }

    override fun cancel() {
        interactor.cancel()
    }

    override fun threeDSecureHandled() {
        interactor.threeDSecureHandled()
    }

    override fun sendClarificationFields(fields: List<ClarificationFieldValue>) {
        interactor.sendClarificationFields(fields)
    }

    override fun sendCustomerFields(fields: List<CustomerFieldValue>) {
        interactor.sendCustomerFields(fields)
    }
}

internal class PayInteractorProxyMockImpl(
    private val onSendRequestCalled: (delegate: PayDelegate?) -> Unit
) : PayInteractorProxy {

    override var delegate: PayDelegate? = null

    override fun sendRequest(request: PayRequest) {
        onSendRequestCalled.invoke(delegate)
    }

    override fun cancel() {
    }

    override fun threeDSecureHandled() {
    }

    override fun sendClarificationFields(fields: List<ClarificationFieldValue>) {
    }

    override fun sendCustomerFields(fields: List<CustomerFieldValue>) {
    }
}