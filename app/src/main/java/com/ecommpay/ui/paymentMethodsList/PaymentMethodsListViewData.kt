package com.ecommpay.ui.paymentMethodsList

import com.ecommpay.ui.base.ViewData
import com.ecommpay.ui.paymentMethodsList.itemPaymentMethod.ItemPaymentMethodViewData

data class PaymentMethodsListViewData(
    val paymentMethodList: List<ItemPaymentMethodViewData>
) : ViewData