package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.paymentpage.msdk.ui.R


internal class Images(
    defaultCardLogo: Int,
    cardScanningLogo: Int,
    apsDefaultLogo: Int,
    googlePayMethodResId: Int,
    googlePayLogo: Int,
    successLogo: Int,
    errorLogo: Int,
    cvvInfoLogo: Int,
) {
    var defaultCardLogo by mutableStateOf(defaultCardLogo)
        private set
    var cardScanningLogo by mutableStateOf(cardScanningLogo)
        private set
    var apsDefaultLogoResId by mutableStateOf(apsDefaultLogo)
        private set
    var googlePayMethodResId by mutableStateOf(googlePayMethodResId)
        private set
    var googlePayLogo by mutableStateOf(googlePayLogo)
        private set
    var successLogo by mutableStateOf(successLogo)
        private set
    var errorLogo by mutableStateOf(errorLogo)
        private set
    var cvvInfoLogo by mutableStateOf(cvvInfoLogo)
        private set
}

internal fun lightImages(): Images = Images(
    defaultCardLogo = R.drawable.ic_card_logo,
    cardScanningLogo = R.drawable.card_scanning_logo_light,
    apsDefaultLogo = R.drawable.aps_default_logo_light,
    googlePayMethodResId = R.drawable.payment_method_google_pay,
    googlePayLogo = R.drawable.googlepay_logo_light,
    successLogo = R.drawable.ic_result_success_light,
    errorLogo = R.drawable.ic_result_failed_light,
    cvvInfoLogo = R.drawable.cvv_info_icon_light
)

internal fun darkImages(): Images = Images(
    defaultCardLogo = R.drawable.ic_card_logo,
    cardScanningLogo = R.drawable.card_scanning_logo_dark,
    apsDefaultLogo = R.drawable.aps_default_logo_dark,
    googlePayMethodResId = R.drawable.payment_method_google_pay,
    googlePayLogo = R.drawable.googlepay_logo_dark,
    successLogo = R.drawable.ic_result_success_light,
    errorLogo = R.drawable.ic_result_failed_light,
    cvvInfoLogo = R.drawable.cvv_info_icon_dark
)


internal val LocalImages = staticCompositionLocalOf { lightImages() }