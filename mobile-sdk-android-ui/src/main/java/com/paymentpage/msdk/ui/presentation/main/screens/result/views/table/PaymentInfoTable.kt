package com.paymentpage.msdk.ui.presentation.main.screens.result.views.table

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.paymentDateToPatternDate
import com.paymentpage.msdk.ui.views.common.SDKTable

@Composable
internal fun PaymentInfoTable(
    actionType: SDKActionType,
    method: UIPaymentMethod?,
    paymentInfo: PaymentInfo,
    payment: Payment,
) {
    val valueTitleCardWallet = when (method) {
        is UIPaymentMethod.UICardPayPaymentMethod,
        is UIPaymentMethod.UISavedCardPayPaymentMethod,
        -> {
            "${payment.account?.type?.uppercase() ?: ""} ${payment.account?.number}"
        }
        is UIPaymentMethod.UIApsPaymentMethod -> {
            method.paymentMethod.name
                ?: getStringOverride(
                    method.paymentMethod.translations[OverridesKeys.TITLE] ?: ""
                )
        }
        is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            method.paymentMethod.name ?: getStringOverride(OverridesKeys.GOOGLE_PAY_HOST_TITLE)
        }
        else -> {
            if (payment.paymentMethodType == PaymentMethodType.CARD)
                "${payment.account?.type?.uppercase() ?: ""} ${payment.account?.number}"
            else
                "${payment.methodName}"
        }
    }

    val cardWallet = getStringOverride(OverridesKeys.TITLE_CARD_WALLET) to
            valueTitleCardWallet

    val paymentId = getStringOverride(OverridesKeys.TITLE_PAYMENT_ID) to
            "${payment.id}"

    val paymentDate = getStringOverride(OverridesKeys.TITLE_PAYMENT_DATE) to
            payment.date?.paymentDateToPatternDate("dd.MM.yyyy HH:mm")

    val paymentDescription =
        getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_DESCRIPTION) to
                if (actionType == SDKActionType.Verify) paymentInfo.paymentDescription else null

    val resultTableMap: Map<String?, String?> = mapOf(
        cardWallet,
        paymentId,
        paymentDate,
        paymentDescription,
    )

    SDKTable(
        tableMap = resultTableMap,
        labelTextStyle = SDKTheme.typography.s14Light.copy(color = SDKTheme.colors.grey),
        valueTextStyle = SDKTheme.typography.s14Normal,
        spaceBetweenItems = 15.dp
    )

}