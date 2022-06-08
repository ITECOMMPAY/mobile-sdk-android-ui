package com.ecommpay.msdk.ui.entry

import com.ecommpay.msdk.ui.base.ShimmerViewData
import com.ecommpay.msdk.ui.base.ViewData

data class EntryViewData(
    val topAppBarTitle: String,
    val paymentDetailsTitle: String,
    val paymentMethodList: List<ViewData>,
) : ViewData {

    companion object {
        val defaultViewData = EntryViewData(
            topAppBarTitle = "Payment Methods",
            paymentDetailsTitle = "",
            paymentMethodList = listOf(
                ShimmerViewData,
                ShimmerViewData,
                ShimmerViewData
            )
        )
    }
}