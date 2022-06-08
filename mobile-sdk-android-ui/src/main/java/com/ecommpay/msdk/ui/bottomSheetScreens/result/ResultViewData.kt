package com.ecommpay.msdk.ui.bottomSheetScreens.result

import com.ecommpay.msdk.ui.base.ViewData

data class ResultViewData(
    val amount: String,
    val currency: String,
    val resultTitle: String,

    val cardWalletTitle: String,
    val cardWalletValue: String,

    val paymentIdTitle: String,
    val paymentIdValue: String,

    val paymentDateTitle: String,
    val paymentDateValue: String,

    val vatAmountTitle: String,
    val vatAmountValue: String,

    val vatCurrencyTitle: String,
    val vatCurrencyValue: String,

    val vatRateTitle: String,
    val vatRateValue: String
) : ViewData {
    companion object {
        val defaultViewData = ResultViewData(
            amount = "10",
            currency = "USD",
            resultTitle = "Payment is successful",

            cardWalletTitle = "cardWalletTitle",
            cardWalletValue = "****1234",

            paymentIdTitle = "paymentIdTitle",
            paymentIdValue = "paymentIdValue",

            paymentDateTitle = "paymentDateTitle",
            paymentDateValue = "06.05.2022",

            vatAmountTitle = "vatAmountTitle",
            vatAmountValue = "0.82",

            vatCurrencyTitle = "vatCurrencyTitle",
            vatCurrencyValue = "USD",

            vatRateTitle = "vatRateTitle",
            vatRateValue = "8.8%"
        )
    }
}
