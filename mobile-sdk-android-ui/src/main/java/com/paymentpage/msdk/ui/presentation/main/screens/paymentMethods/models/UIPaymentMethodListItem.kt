package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models

import androidx.compose.runtime.Immutable

@Immutable
internal data class UIPaymentMethodListItem(
    val method: UIPaymentMethod,
    val isSelected: Boolean
)
