package com.paymentpage.msdk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

internal data class Shapes(
    val radius4: RoundedCornerShape = RoundedCornerShape(4.dp),
    val radius6: RoundedCornerShape = RoundedCornerShape(6.dp),
    val radius8: RoundedCornerShape = RoundedCornerShape(8.dp),
    val radius12: RoundedCornerShape = RoundedCornerShape(12.dp),
    val radius16: RoundedCornerShape = RoundedCornerShape(16.dp),
    val radius20: RoundedCornerShape = RoundedCornerShape(20.dp),
    val radius32: RoundedCornerShape = RoundedCornerShape(32.dp),
    val radius64: RoundedCornerShape = RoundedCornerShape(64.dp)
)

internal val LocalShapes = staticCompositionLocalOf { Shapes() }