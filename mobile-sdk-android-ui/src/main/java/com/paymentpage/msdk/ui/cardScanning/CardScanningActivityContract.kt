package com.paymentpage.msdk.ui.cardScanning

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

internal class CardScanningActivityContract :
    ActivityResultContract<CardScanningActivityContract.Config, CardScanningActivityContract.Result>() {

    override fun createIntent(context: Context, input: Config): Intent =
        TODO("CardIO. Implement card scanning flow")

    override fun parseResult(resultCode: Int, intent: Intent?): Result {
        //TODO("CardIO. Return back scanning result parsing")
//        var result: CreditCard? = null
//        if (intent != null && intent.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
//            result = intent.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
//        val expiryMonth = result?.expiryMonth.oneDigitMonthToTwoDigitMonth()
//        val expiryYear = result?.expiryYear.fourDigitYearToTwoDigitYear()
//        val isValidExpiry = expiryMonth != null && expiryYear != null

        return Result(
            pan = null,
            cardHolderName = null,
            expiry = null
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