package com.ecommpay.msdk.ui.paymentMethodsList


import com.ecommpay.msdk.core.MSDKCoreSession
import com.ecommpay.msdk.core.MSDKCoreSessionConfig
import com.ecommpay.msdk.core.domain.entities.init.InitPaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.InitSavedAccount
import com.ecommpay.msdk.core.domain.entities.init.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.core.domain.interactors.init.InitDelegate
import com.ecommpay.msdk.core.domain.interactors.init.InitRequest
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.navigation.NavigationViewActions
import com.ecommpay.msdk.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.utils.SignatureGenerator

class PaymentMethodsListViewModel :
    BaseViewModel<PaymentMethodsListViewData, PaymentMethodsListViewIntents>() {

    override fun reduce(
        intent: PaymentMethodsListViewIntents,
        currentState: DefaultViewStates.Display<PaymentMethodsListViewData>,
    ) {
        when (intent) {
            is PaymentMethodsListViewIntents.Click -> {
                launchAction(NavigationViewActions.PaymentMethodsListScreenToPaymentMethodScreen(
                    name = intent.name))
            }
        }
    }

    override fun entryPoint() {
        getData()
    }

    override fun defaultViewData() =
        PaymentMethodsListViewData(
            paymentMethodList = listOf(
                ItemPaymentMethodViewData(
                    name = "card",
                ),
                ItemPaymentMethodViewData(
                    name = "card2",
                )
            )
        )

    //Testing API
    private fun getData() {
        val config = MSDKCoreSessionConfig("pp-sdk.westresscode.net", "paymentpage.ecommpay.com")
        val msdkSession = MSDKCoreSession(config)
        val interactor = msdkSession.getInitInteractor()

        val paymentInfo = PaymentInfo(
            projectId = 627,
            //paymentId = "msdk_core_payment_id_" + getRandomNumber(),
            paymentId = "sdk_android_1650617310",
            paymentAmount = 1000,
            paymentCurrency = "RUB",
            customerId = "12"
        )

        paymentInfo.signature =
            SignatureGenerator.generateSignature(paymentInfo.getParamsForSignature(), "123")
        updateState(DefaultViewStates.Loading(viewState.value?.viewData ?: defaultViewData()))
        interactor.execute(InitRequest(paymentInfo, null), object : InitDelegate {
            override fun onInitReceived(
                cardLogos: Map<String, String>,
                paymentMethods: List<InitPaymentMethod>,
                cardTypes: Map<String, String>,
                translations: Map<String, String>,
                savedAccounts: List<InitSavedAccount>,
            ) {
                updateState(DefaultViewStates.Display(PaymentMethodsListViewData(
                    paymentMethods.map { it.toViewData() }
                )))
            }

            override fun onPaymentRestored(payment: Payment) {
                updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                    ?: defaultViewData()))
            }


            override fun onError(error: Throwable, message: String?) {
                updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                    ?: defaultViewData()))
                launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                    message ?: "",
                    true)))
            }
        })

    }

    fun InitPaymentMethod.toViewData() = ItemPaymentMethodViewData(
        name = name ?: ""
    )

//    private fun codeToIcon(code: String): Int {
//        return when (code) {
//            "card" -> {
//                R.drawable.btn_star_big_on
//            }
//            "qiwi" -> {
//                R.drawable.ic_menu_more
//            }
//            else -> {
//                R.drawable.ic_delete
//            }
//        }
//    }
}