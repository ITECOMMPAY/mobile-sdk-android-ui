package com.paymentpage.msdk.ui.cardScanning

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.paymentpage.msdk.ui.base.Constants.HIDE_CARDIO_LOGO
import com.paymentpage.msdk.ui.base.Constants.REQUIRE_CARDHOLDER_NAME
import com.paymentpage.msdk.ui.base.Constants.REQUIRE_EXPIRY
import com.paymentpage.msdk.ui.base.Constants.SCAN_EXPIRY
import com.paymentpage.msdk.ui.base.Constants.SCAN_WITHOUT_CAMERA
import com.paymentpage.msdk.ui.base.Constants.SHOW_PAYPAL_LOGO_ON_ACTION_BAR
import com.paymentpage.msdk.ui.base.Constants.SUPPRESS_CONFIRMATION
import com.paymentpage.msdk.ui.base.Constants.SUPPRESS_MANUAL_ENTRY
import com.paymentpage.msdk.ui.base.Constants.USE_THEME_APP
import com.paymentpage.msdk.ui.utils.extensions.core.fourDigitYearToTwoDigitYear
import com.paymentpage.msdk.ui.utils.extensions.core.oneDigitMonthToTwoDigitMonth
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard

internal class CardScanningActivityContract :
    ActivityResultContract<CardScanningActivityContract.Config, CardScanningActivityContract.Result>() {

    override fun createIntent(context: Context, input: Config): Intent =
        Intent(context, CardIOActivity::class.java).apply {
            putExtra(CardIOActivity.EXTRA_NO_CAMERA, SCAN_WITHOUT_CAMERA)
            putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, REQUIRE_CARDHOLDER_NAME)
            putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, SCAN_EXPIRY)
            putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, REQUIRE_EXPIRY)
            putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, HIDE_CARDIO_LOGO)
            putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, SHOW_PAYPAL_LOGO_ON_ACTION_BAR)
            putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, USE_THEME_APP)
            putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, SUPPRESS_CONFIRMATION)
            putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, SUPPRESS_MANUAL_ENTRY)
            putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, input.brandColor)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Result {
        var result: CreditCard? = null
        if (intent != null && intent.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
            result = intent.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
        val expiryMonth = result?.expiryMonth.oneDigitMonthToTwoDigitMonth()
        val expiryYear = result?.expiryYear.fourDigitYearToTwoDigitYear()
        val isValidExpiry = expiryMonth != null && expiryYear != null
        return Result(
            pan = result?.cardNumber,
            cardHolderName = result?.cardholderName,
            expiry = if (isValidExpiry) "$expiryMonth/$expiryYear" else null
        )
    }

    internal class Config(
        val brandColor: Int,
    )

    internal data class Result(
        val pan: String? = null,
        val cardHolderName: String? = null,
        val expiry: String? = null
    )
}