package com.paymentpage.msdk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

internal data class Shapes(
    val radius6: RoundedCornerShape = RoundedCornerShape(6.dp),
    val radius12: RoundedCornerShape = RoundedCornerShape(12.dp)
)

internal val LocalShapes = staticCompositionLocalOf { Shapes() }