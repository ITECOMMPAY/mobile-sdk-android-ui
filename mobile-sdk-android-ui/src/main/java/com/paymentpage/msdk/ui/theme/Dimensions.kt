package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class Dimensions(
    val padding4: Dp = 4.dp,
    val padding5: Dp = 5.dp,
    val padding8: Dp = 8.dp,
    val padding10: Dp = 10.dp,
    val padding12: Dp = 12.dp,
    val padding15: Dp = 15.dp,
    val padding20: Dp = 20.dp,
    val padding22: Dp = 22.dp,
    val padding25: Dp = 25.dp
)

internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }