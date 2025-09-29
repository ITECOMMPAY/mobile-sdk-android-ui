package com.paymentpage.msdk.ui.presentation.main.screens.result.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.table.RecurrentInfoTable
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.table.CompleteFieldsTable
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.table.PaymentInfoTable
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
import com.paymentpage.msdk.ui.utils.extensions.core.RecurrentTypeUI
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.isShowRecurringUI
import com.paymentpage.msdk.ui.utils.extensions.core.typeUI
import com.paymentpage.msdk.ui.views.common.SDKDivider

@Composable
internal fun ResultContent(
    onError: (ErrorResult, Boolean) -> Unit,
) {
    val paymentOptions = LocalPaymentOptions.current
    val mainViewModel = LocalMainViewModel.current
    val method = LocalPaymentMethodsViewModel.current.lastState.currentMethod
    val payment = mainViewModel.payment

    //additional info
    val completeFields = payment?.completeFields
    val recurrentInfo = paymentOptions.recurrentInfo

    var isDividerVisible by remember { mutableStateOf(true) }

    if (payment == null) {
        onError(
            ErrorResult(
                code = ErrorCode.PAYMENT_NOT_FOUND,
                message = "Not found payment in State"
            ), true
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = if (!SDKTheme.colors.isDarkTheme) Color.White else SDKTheme.colors.container,
                    shape = SDKTheme.shapes.radius12
                ).padding(25.dp)
        ) {
            if (
                recurrentInfo != null &&
                recurrentInfo.typeUI() == RecurrentTypeUI.REGULAR &&
                recurrentInfo.isShowRecurringUI()
            ) {
                if (payment.recurringId == null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = SohneBreitFamily,
                        style = SDKTheme.typography.s14Normal.copy(
                            color = SDKTheme.colors.red,
                            textAlign = TextAlign.Center
                        ),
                        text = getStringOverride(OverridesKeys.RECURRING_FAIL)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

                RecurrentInfoTable(
                    actionType = paymentOptions.actionType,
                    paymentInfo = paymentOptions.paymentInfo,
                    recurrentInfo = recurrentInfo,
                    labelTextStyle = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.grey),
                    valueTextStyle = SDKTheme.typography.s14Normal,
                    spaceBetweenItems = 15.dp,
                    isTableEmptyCallback = { isDividerVisible = !it }
                )

                if (isDividerVisible) {
                    Spacer(modifier = Modifier.height(15.dp))
                    SDKDivider(
                        color = SDKTheme.colors.mediumGrey,
                        isDashed = true
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }

            PaymentInfoTable(
                actionType = paymentOptions.actionType,
                method = method,
                paymentInfo = paymentOptions.paymentInfo,
                payment = payment
            )

            if (!completeFields.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(15.dp))
                CompleteFieldsTable(
                    completeFields = completeFields
                )
            }
        }
    }
}