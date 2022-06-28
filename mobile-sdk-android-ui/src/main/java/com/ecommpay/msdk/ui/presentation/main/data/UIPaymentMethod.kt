package com.ecommpay.msdk.ui.presentation.main.data

data class UIPaymentMethod(
    val code: String,
    val name: String,
    val iconUrl: String? = "",
) {
    companion object {
        fun previewData(count: Int) = (0..count).map {
            UIPaymentMethod(
                code = "card",
                name = "card $it"
            )
        }
    }
}