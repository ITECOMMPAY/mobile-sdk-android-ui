package com.ecommpay.msdk.ui.bottomSheetScreens.result

import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.initData
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.payment
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.paymentInfo

class ResultViewModel: BaseViewModel<ResultViewData>() {

    override fun entryPoint() {
        val viewData = toViewData()
        updateState(DefaultViewStates.Display(viewData = viewData))
    }

    override fun obtainIntent(intent: ViewIntents, currentState: ViewStates<ResultViewData>) {
        when(intent) {
            is ResultViewIntents.ClickDone -> {
                launchAction(DefaultViewActions.SetResult(0))
            }
        }
    }

    override fun defaultViewData() = ResultViewData.defaultViewData

    private fun toViewData() = ResultViewData(
        amount = paymentInfo.paymentAmount,
        currency = paymentInfo.paymentCurrency,
        resultTitle = initData.translations["title_result_succes_payment"] ?: "",

        cardWalletTitle = initData.translations["title_card_wallet"] ?: "",
        cardWalletValue = "${payment.account?.type?.uppercase()} ${payment.account?.number?.takeLast(8)}",

        paymentIdTitle = initData.translations["title_payment_id"] ?: "",
        paymentIdValue = paymentInfo.paymentId,

        paymentDateTitle = initData.translations["title_payment_date"] ?: "",
        paymentDateValue = payment.date ?: "",

        vatAmountTitle = initData.translations[payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_amount"
        }?.name ?: ""] ?: "",
        vatAmountValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_amount"
        }?.value ?: "",

        vatCurrencyTitle = initData.translations[payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_currency"
        }?.name ?: ""] ?: "",
        vatCurrencyValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_currency"
        }?.value ?: "",

        vatRateTitle = initData.translations[payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_rate"
        }?.name ?: ""] ?: "",
        vatRateValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_rate"
        }?.value ?: "",
    )
}