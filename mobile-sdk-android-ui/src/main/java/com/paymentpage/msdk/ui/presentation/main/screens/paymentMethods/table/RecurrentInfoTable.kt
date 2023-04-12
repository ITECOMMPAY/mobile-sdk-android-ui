package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.table

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.utils.extensions.core.*
import com.paymentpage.msdk.ui.views.common.SDKTable

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
    val recurringStartDateValue = recurrentInfo.startDateUI()

    //recurring amount
    val recurringAmountLabel = getStringOverride(OverridesKeys.RECURRING_AMOUNT)
    val recurringAmountValue = recurrentInfo.amountUI(paymentInfo)

    //recurring expiry date
    val recurringExpiryDateLabel = getStringOverride(OverridesKeys.RECURRING_TYPE_EXPIRY_DATE)
    val recurringExpiryDateValue = recurrentInfo.expiryDateUI()

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
