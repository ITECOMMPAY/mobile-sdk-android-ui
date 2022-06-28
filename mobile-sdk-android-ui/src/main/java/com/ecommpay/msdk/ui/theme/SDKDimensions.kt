package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class SDKDimensions(
    val paddingDp5: Dp = 5.dp,
    val paddingDp10: Dp = 10.dp,
    val paddingDp15: Dp = 15.dp,
    val paddingDp20: Dp = 20.dp,
    val paddingDp25: Dp = 25.dp,
    val paymentMethodItemHeight: Dp = 50.dp
)

internal val LocalDimensions = staticCompositionLocalOf { SDKDimensions() }