package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.ui.PaymentActivity

internal fun getStringOverride(key: String): String =
    PaymentActivity.stringResourceManager.getStringByKey(key) ?: key