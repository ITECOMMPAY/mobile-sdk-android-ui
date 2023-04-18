package com.paymentpage.msdk.ui.views.recurring

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.RecurrentTypeUI
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.isShowRecurringUI
import com.paymentpage.msdk.ui.utils.extensions.core.typeUI

@Composable
internal fun RecurringAgreements() {
    val paymentOptions = LocalPaymentOptions.current
    val recurrentInfo = paymentOptions.recurrentInfo
    var testTag = ""
    if (recurrentInfo != null && recurrentInfo.isShowRecurringUI()) {
        val recurringAgreementsLabel = when {
            recurrentInfo.typeUI() == RecurrentTypeUI.EXPRESS -> {
                testTag = TestTagsConstants.RECURRING_AGREEMENTS_EXPRESS_LABEL_TEXT
                getStringOverride(OverridesKeys.RECURRING_TYPE_EXPRESS)
            }
            recurrentInfo.typeUI() == RecurrentTypeUI.REGULAR -> {
                testTag = TestTagsConstants.RECURRING_AGREEMENTS_REGULAR_LABEL_TEXT
                getStringOverride(OverridesKeys.RECURRING_TYPE_REGULAR)
            }
            else -> ""
        }
        if (recurringAgreementsLabel.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(testTag),
                style = SDKTheme.typography.s12Light.copy(
                    color = if (!SDKTheme.colors.isDarkTheme)
                        SDKTheme.colors.grey
                    else
                        SDKTheme.colors.neutral,
                    textAlign = TextAlign.Center
                ),
                text = recurringAgreementsLabel
            )
        }
    }
}
