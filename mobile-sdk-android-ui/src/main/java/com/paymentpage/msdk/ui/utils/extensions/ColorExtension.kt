package com.paymentpage.msdk.ui.utils.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix

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