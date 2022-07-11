package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.ecommpay.msdk.ui.R


internal class Images(
    sdkLogoResId: Int,
    cardLogoResId: Int
) {
    var sdkLogoResId by mutableStateOf(sdkLogoResId)
        private set
    var cardLogoResId by mutableStateOf(cardLogoResId)
        private set
}

internal fun lightImages(): Images = Images(
    sdkLogoResId = R.drawable.sdk_logo,
    cardLogoResId = R.drawable.card_logo,
)


internal val LocalImages = staticCompositionLocalOf { lightImages() }