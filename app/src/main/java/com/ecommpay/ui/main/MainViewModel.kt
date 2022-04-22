package com.ecommpay.ui.main

import android.R
import com.ecommpay.sdk.ECMPPaymentInfo
import com.ecommpay.ui.base.*
import com.ecommpay.ui.main.itemPaymentMethod.ItemPaymentMethodViewData
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

class MainViewModel : BaseViewModel<MainViewData, MainViewIntents>() {
    override fun obtainIntent(intent: MainViewIntents, currentState: ViewStates<MainViewData>) {
        when (intent) {
            is MainViewIntents.Click -> {
                launchAction(action = MainViewActions.ShowToast("Hello, ${intent.name}"))
            }
        }
    }

    override fun entryPoint() {
        getData()
    }

    override fun defaultViewData() =
        MainViewData(
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
                updateState(DefaultViewStates.Display(MainViewData(
                    paymentMethods.map { it.toViewData() }
                )))
            }

            override fun onError(exception: java.lang.Exception?, isCritical: Boolean) {

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