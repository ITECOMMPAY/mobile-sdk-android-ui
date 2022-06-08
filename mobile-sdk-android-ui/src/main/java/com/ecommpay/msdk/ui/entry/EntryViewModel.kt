package com.ecommpay.msdk.ui.entry

import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.core.domain.interactors.init.InitDelegate
import com.ecommpay.msdk.core.domain.interactors.init.InitRequest
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.msdkSession
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.paymentInfo

class EntryViewModel : BaseViewModel<EntryViewData>() {

    override fun obtainIntent(
        intent: ViewIntents,
        currentState: ViewStates<EntryViewData>,
    ) {
        when (intent) {

        }
    }

    override fun entryPoint() {
        getData()
    }

    override fun defaultViewData() = EntryViewData.defaultViewData

    //Testing API
    private fun getData() {
        updateState(DefaultViewStates.Loading(viewState.value?.viewData ?: defaultViewData()))
        val initInteractor = msdkSession.getInitInteractor()
        initInteractor.execute(
            request = InitRequest(
                paymentInfo = paymentInfo,
                recurrentInfo = null,
                threeDSecureInfo = null
            ),
            callback = object : InitDelegate {
                //Init
                override fun onInitReceived() {
                    updateState(
                        DefaultViewStates.Display(
                            EntryViewData(
                                paymentMethodList = msdkSession.getPaymentMethods()?.map { it.toViewData() } ?: emptyList(),
                                topAppBarTitle = msdkSession.getStringResourceManager().payment.methodsTitle ?: "",
                            paymentDetailsTitle = "Payment Details")))
                }
                override fun onError(message: String, code: String?) {
                    updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                        ?: defaultViewData()))
                    launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                        message ?: "",
                        true)))
                }

                //Restore payment
                override fun onPaymentRestored(payment: Payment) {

                }
            }
        )
    }


    fun PaymentMethod.toViewData() = ItemPaymentMethodViewData(
        name = name ?: "",
        iconUrl = iconUrl ?: ""
    )
}