package com.ecommpay.msdk.ui.paymentMethodsList

import com.ecommpay.msdk.ui.base.ViewData
import com.ecommpay.msdk.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethodViewData

data class PaymentMethodsListViewData(
    val paymentMethodList: List<ItemPaymentMethodViewData>
) : ViewData