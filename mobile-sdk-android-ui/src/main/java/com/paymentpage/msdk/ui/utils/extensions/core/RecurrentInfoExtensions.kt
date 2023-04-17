package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.patternDateToPatternDate
import java.util.Locale

private fun RecurrentInfo.period(): RecurrentPeriod? =
    when (this.period) {
        "D" -> RecurrentPeriod.DAILY
        "W" -> RecurrentPeriod.WEEKLY
        "M" -> RecurrentPeriod.MONTHLY
        "Q" -> RecurrentPeriod.QUARTERLY
        "Y" -> RecurrentPeriod.ANNUALLY
        else -> null
    }

internal fun RecurrentInfo.periodUI(): String? =
    if (this.period() != null && (this.interval == null || this.interval == 1))
        when (this.period()) {
            RecurrentPeriod.DAILY -> getStringOverride(OverridesKeys.RECURRING_PERIOD_DAILY)
            RecurrentPeriod.WEEKLY -> getStringOverride(OverridesKeys.RECURRING_PERIOD_WEEKLY)
            RecurrentPeriod.MONTHLY -> getStringOverride(OverridesKeys.RECURRING_PERIOD_MONTHLY)
            RecurrentPeriod.QUARTERLY -> getStringOverride(OverridesKeys.RECURRING_PERIOD_QUARTERLY)
            RecurrentPeriod.ANNUALLY -> getStringOverride(OverridesKeys.RECURRING_PERIOD_ANNUALLY)
            else -> null
        }
    else null


internal fun RecurrentInfo.amountUI(paymentInfo: PaymentInfo): String =
    if (this.amount == null)
        "${paymentInfo.paymentAmount.amountToCoins()} ${paymentInfo.paymentCurrency.uppercase()}"
    else
        "${this.amount.amountToCoins()} ${paymentInfo.paymentCurrency.uppercase()}"

internal fun RecurrentInfo.chargedAmountUI(
    actionType: SDKActionType,
    paymentInfo: PaymentInfo
): String? =
    if (
        this.typeUI() == RecurrentTypeUI.REGULAR &&
        actionType == SDKActionType.Verify &&
        this.periodUI() != null
    )
        "0.00 ${paymentInfo.paymentCurrency.uppercase()}"
    else
        null

internal fun RecurrentInfo.typeUI(): RecurrentTypeUI? =
    when (this.type) {
        "R" -> RecurrentTypeUI.REGULAR
        "C", "U", "I" -> RecurrentTypeUI.EXPRESS
        "" -> RecurrentTypeUI.EXPRESS
        null -> RecurrentTypeUI.EXPRESS
        else -> null //Incorrect type
    }

internal fun RecurrentInfo.expiryDateUI(
    locale: Locale?
): String? {
    return if (
        this.expiryDay != null &&
        this.expiryMonth != null &&
        this.expiryYear != null
    ) {
        "${this.expiryDay}-${this.expiryMonth}-${this.expiryYear}"
            .patternDateToPatternDate(
                inPattern = "dd-MM-yyyy",
                outPattern = "MMMM dd, yyyy",
                locale = locale
            )
    } else return null
}

internal fun RecurrentInfo.startDateUI(
    locale: Locale?
): String? {
    val startDate = this.startDate
    return if (startDate != null) {
        startDate.patternDateToPatternDate(
            inPattern = "dd-MM-yyyy",
            outPattern = "MMMM dd, yyyy",
            locale = locale
        )
    } else return null
}

internal fun RecurrentInfo?.isShowRecurringUI(): Boolean {
    val recurringInfo = this ?: return false
    return recurringInfo.register
}

internal enum class RecurrentTypeUI {
    REGULAR,
    EXPRESS,
}

internal enum class RecurrentPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    QUARTERLY,
    ANNUALLY,
}