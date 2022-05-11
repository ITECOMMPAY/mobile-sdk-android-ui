package com.ecommpay.msdk.ui.main

import com.ecommpay.msdk.core.domain.entities.init.InitPaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.InitSavedAccount

data class InitData(
    val paymentMethods: List<InitPaymentMethod>,
    val translations: Map<String, String>,
    val savedAccounts: List<InitSavedAccount>,
    val secureLogos: Map<String, String>
)
