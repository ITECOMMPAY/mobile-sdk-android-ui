package com.ecommpay.msdk.ui.bottomSheetScreens.result

import com.ecommpay.msdk.ui.Utils
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.payment
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.paymentInfo
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.stringResourceManager

class ResultViewModel: BaseViewModel<ResultViewData>() {

    override fun entryPoint() {
        updateState(DefaultViewStates.Display(viewData = mapDataToViewData()))
    }

    override fun obtainIntent(intent: ViewIntents, currentState: ViewStates<ResultViewData>) {
        when(intent) {
            is ResultViewIntents.ClickDone -> {
                launchAction(DefaultViewActions.SetResult(0))
            }
        }
    }

    override fun defaultViewData() = ResultViewData.defaultViewData

    private fun mapDataToViewData() = ResultViewData(
        amount = Utils.getFormattedAmount(payment.sum),
        currency = payment.currency ?: "",
        resultTitle = stringResourceManager.payment.successPaymentTitle ?: "",

        cardWalletTitle = stringResourceManager.getStringByKey("title_card_wallet") ?: "",
        cardWalletValue = "${payment.account?.type?.uppercase()} ${payment.account?.number?.takeLast(8)}",

        paymentIdTitle = stringResourceManager.payment.infoPaymentIdTitle ?: "",
        paymentIdValue = paymentInfo.paymentId,

        paymentDateTitle = stringResourceManager.payment.infoPaymentDateTitle ?: "",
        paymentDateValue = payment.date ?: "",

        vatAmountTitle = stringResourceManager.getStringByKey(payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_amount"
        }?.name ?: "") ?: "",
        vatAmountValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_amount"
        }?.value ?: "",

        vatCurrencyTitle = stringResourceManager.getStringByKey(payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_currency"
        }?.name ?: "") ?: "",
        vatCurrencyValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_currency"
        }?.value ?: "",

        vatRateTitle = stringResourceManager.getStringByKey(payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_rate"
        }?.name ?: "") ?: "",
        vatRateValue = payment.completeFields?.find {
            it.name == "complete_field_payment_vat_operation_rate"
        }?.value ?: "",
    )
}