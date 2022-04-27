package com.ecommpay.ui.paymentMethodsList

import android.R
import com.ecommpay.sdk.ECMPPaymentInfo
import com.ecommpay.ui.base.*
import com.ecommpay.ui.navigation.NavigationViewActions
import com.ecommpay.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.utils.SignatureGenerator
import com.paymentpagesdk.core.api.ApiModule
import com.paymentpagesdk.core.components.presenters.PaymentMethodHelper
import com.paymentpagesdk.core.components.presenters.paymenttype.card.interfaces.SignatureValidationTaskCallbacks
import com.paymentpagesdk.core.components.tasks.SignatureValidationTask
import com.paymentpagesdk.core.entities.init.LogoCardType
import com.paymentpagesdk.core.entities.init.SavedAccountEntity
import com.paymentpagesdk.core.entities.init.SecureLogo
import com.paymentpagesdk.core.entities.paymentmethod.SDKSupportedPaymentMethod
import com.paymentpagesdk.core.entities.status.PaymentEntity
import java.util.HashMap

class PaymentMethodsListViewModel :
    BaseViewModel<PaymentMethodsListViewData, PaymentMethodsListViewIntents>() {

    override fun reduce(
        intent: PaymentMethodsListViewIntents,
        currentState: DefaultViewStates.Display<PaymentMethodsListViewData>,
    ) {
        when (intent) {
            is PaymentMethodsListViewIntents.Click -> {
                launchAction(NavigationViewActions.PaymentMethodsListScreenToPaymentMethodScreen(name = intent.name))
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
                    icon = R.drawable.btn_star_big_on,
                    name = "card",
                ),
                ItemPaymentMethodViewData(
                    icon = R.drawable.ic_delete,
                    name = "card2",
                )
            )
        )

    //Testing API
    private fun getData() {
        updateState(DefaultViewStates.Loading(PaymentMethodsListViewData(emptyList())))
        val apiModule = ApiModule("https://pp-sdk.westresscode.net/v1/")
        val paymentData = ECMPPaymentInfo(627, "asdasdtesttest324", 100, "RUB")
        val signature = SignatureGenerator.generateSignature(paymentData.paramsForSignature, "123")
        paymentData.signature = signature
        SignatureValidationTask(paymentData, apiModule, object : SignatureValidationTaskCallbacks {
            override fun onSuccess(
                sessionID: String?,
                accounts: List<SavedAccountEntity>,
                paymentMethods: List<SDKSupportedPaymentMethod>,
                cardTypes: Array<out LogoCardType>,
                secureLogos: Array<out SecureLogo>,
                payment: PaymentEntity?,
                translations: HashMap<String, String>,
            ) {
                updateState(DefaultViewStates.Display(PaymentMethodsListViewData(
                    paymentMethods.map { it.toViewData() }
                )))
            }

            override fun onError(exception: java.lang.Exception?, isCritical: Boolean) {
                updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                    ?: defaultViewData()))
                launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                    "Попробуйте ещё раз",
                    true)))
            }
        }).execute()
    }

    fun SDKSupportedPaymentMethod.toViewData() = ItemPaymentMethodViewData(
        name = paymentMethod.name,
        icon = PaymentMethodHelper.getPaymentMethodOnPaymentLogo(method, "ru")
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