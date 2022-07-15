package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.paymentpage.msdk.ui.R


internal class Images(
    sdkLogoResId: Int,
    cardLogoResId: Int,
    googlePayMethodResId: Int
) {
    var sdkLogoResId by mutableStateOf(sdkLogoResId)
        private set
    var cardLogoResId by mutableStateOf(cardLogoResId)
        private set
    var googlePayMethodResId by mutableStateOf(googlePayMethodResId)
        private set
}

internal fun lightImages(): Images = Images(
    sdkLogoResId = R.drawable.sdk_logo,
    cardLogoResId = R.drawable.card_logo,
    googlePayMethodResId = R.drawable.payment_method_google_pay
)


internal val LocalImages = staticCompositionLocalOf { lightImages() }