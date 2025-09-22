package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.table

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.amountUI
import com.paymentpage.msdk.ui.utils.extensions.core.chargedAmountUI
import com.paymentpage.msdk.ui.utils.extensions.core.expiryDateUI
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.periodUI
import com.paymentpage.msdk.ui.utils.extensions.core.startDateUI
import com.paymentpage.msdk.ui.views.common.SDKTable
import java.util.Locale

@Composable
internal fun RecurrentInfoTable(
    actionType: SDKActionType,
    paymentInfo: PaymentInfo,
    recurrentInfo: RecurrentInfo,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle = labelTextStyle,
    spaceBetweenItems: Dp,
    isTableEmptyCallback: ((Boolean) -> Unit)? = null
) {

    val languageCode = paymentInfo.languageCode
    val regionCode = paymentInfo.regionCode

    val locale = when {
        !languageCode.isNullOrEmpty() && !regionCode.isNullOrEmpty() -> Locale(languageCode, regionCode)
        !languageCode.isNullOrEmpty() -> Locale(languageCode)
        else -> null
    }

    //recurring charged right now
    val recurringChargedRightNowLabel = getStringOverride(OverridesKeys.RECURRING_CHARGED_RIGHT_NOW)
    val recurringChargedRightNowValue = recurrentInfo.chargedAmountUI(
        actionType = actionType,
        paymentInfo = paymentInfo
    )

    //recurring period
    val recurringPeriodLabel = getStringOverride(OverridesKeys.RECURRING_PERIOD_LABEL)
    val recurringPeriodValue = recurrentInfo.periodUI()

    //recurring start date
    val recurringStartDateLabel = getStringOverride(OverridesKeys.RECURRING_START_DATE)
    val recurringStartDateValue = recurrentInfo.startDateUI(locale = locale)

    //recurring amount
    val recurringAmountLabel = getStringOverride(OverridesKeys.RECURRING_AMOUNT)
    val recurringAmountValue = recurrentInfo.amountUI(paymentInfo)

    //recurring expiry date
    val recurringExpiryDateLabel = getStringOverride(OverridesKeys.RECURRING_TYPE_EXPIRY_DATE)
    val recurringExpiryDateValue = recurrentInfo.expiryDateUI(locale = locale)

    val labelWithValueMap: Map<String?, String?> = mapOf(
        recurringChargedRightNowLabel to recurringChargedRightNowValue,
        recurringStartDateLabel to recurringStartDateValue,
        recurringAmountLabel to recurringAmountValue,
        recurringPeriodLabel to recurringPeriodValue,
        recurringExpiryDateLabel to recurringExpiryDateValue
    )

    SDKTable(
        tableMap = labelWithValueMap,
        labelTextStyle = labelTextStyle,
        valueTextStyle = valueTextStyle,
        spaceBetweenItems = spaceBetweenItems,
        isTableEmptyCallback = isTableEmptyCallback
    )
}

@Preview
@Composable
fun RecurrentInfoTable_Preview() {
    SDKTheme {
        RecurrentInfoTable(
            actionType = SDKActionType.Sale,
            paymentInfo = PaymentInfo.create(23523, paymentId = "", paymentAmount = 150, paymentCurrency = "RUB"),
            recurrentInfo = RecurrentInfo(register = true),
            labelTextStyle = TextStyle.Default,
            spaceBetweenItems = 10.dp,
        )
    }
}