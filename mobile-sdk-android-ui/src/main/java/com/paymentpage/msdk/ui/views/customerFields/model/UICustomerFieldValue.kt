package com.paymentpage.msdk.ui.views.customerFields.model


internal data class UICustomerFieldValue(
    val name: String,
    val value: String,
    val isValid: Boolean,
    val isRequired: Boolean,
    val isHidden: Boolean
)