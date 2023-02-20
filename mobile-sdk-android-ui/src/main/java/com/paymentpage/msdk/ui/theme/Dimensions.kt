package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class Dimensions(
    val buttonHeight: Dp = 45.dp,
    val googlePayButtonHeight: Dp = 48.dp,
)

internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }