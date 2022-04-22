package com.ecommpay.ui.main

import com.ecommpay.ui.base.ViewData
import com.ecommpay.ui.main.itemPaymentMethod.ItemPaymentMethodViewData

data class MainViewData(
    val paymentMethodList: List<ItemPaymentMethodViewData>
) : ViewData