package com.paymentpage.msdk.ui.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import com.paymentpage.msdk.ui.theme.SDKTheme

fun Color.getColorMatrix(alpha: Float): ColorMatrix {
    val r = this.red
    val g = this.green
    val b = this.blue

    return ColorMatrix(
        floatArrayOf( // Change red channel
            r, 0f, 0f, 0f, 0f, 0f, g, 0f, 0f, 0f, 0f, 0f, b, 0f, 0f, 0f, 0f, 0f, alpha, 0f
        )
    )
}

@Composable
internal fun customColor(brandColor: String?): Color {
    return if (brandColor != null)
        SDKTheme.colors.primary
    else if (SDKTheme.colors.isDarkTheme)
        SDKTheme.colors.neutral
    else
        SDKTheme.colors.primary
}