package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.paymentpage.msdk.ui.R


internal class Images(
    sdkLogoResId: Int,
    cardLogoResId: Int,
    apsDefaultLogo: Int,
    googlePayMethodResId: Int,
    loadingLogo: Int,
    successLogo: Int,
    errorLogo: Int
) {
    var sdkLogoResId by mutableStateOf(sdkLogoResId)
        private set
    var cardLogoResId by mutableStateOf(cardLogoResId)
        private set
    var apsDefaultLogoResId by mutableStateOf(apsDefaultLogo)
        private set
    var googlePayMethodResId by mutableStateOf(googlePayMethodResId)
        private set
    var loadingLogo by mutableStateOf(loadingLogo)
        private set
    var successLogo by mutableStateOf(successLogo)
        private set
    var errorLogo by mutableStateOf(errorLogo)
        private set
}

internal fun lightImages(): Images = Images(
    sdkLogoResId = R.drawable.sdk_logo,
    cardLogoResId = R.drawable.card_logo,
    apsDefaultLogo = R.drawable.aps_default_logo,
    googlePayMethodResId = R.drawable.payment_method_google_pay,
    loadingLogo = R.drawable.loading_logo,
    successLogo = R.drawable.success_logo,
    errorLogo = R.drawable.error_logo
)


internal val LocalImages = staticCompositionLocalOf { lightImages() }