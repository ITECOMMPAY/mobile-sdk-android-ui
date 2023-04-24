package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton
import com.paymentpage.msdk.ui.views.recurring.RecurringAgreements

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun PayButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    textColor: Color = Color.White.copy(
        alpha = when {
            !SDKTheme.colors.isDarkTheme -> 1.0f
            isEnabled -> 1.0f
            //disabled && darkTheme
            else -> 0.3f
        }
    ),
    payLabel: String,
    amount: String,
    currency: String,
    showRecurringAgreement: Boolean = true,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier.semantics(mergeDescendants = true) {},
        isEnabled = isEnabled,
        content = {
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.BUTTON_LABEL_TEXT),
                text = payLabel,
                style = SDKTheme.typography.s16Normal.copy(color = textColor)
            )
            Text(
                modifier = Modifier
                    .semantics {
                        invisibleToUser()
                    },
                text = " "
            )
            Box(
                modifier = Modifier
                    .semantics {
                        contentDescription = "$amount $currency"
                    }
            ) {
                Row {
                    Text(
                        modifier = Modifier
                            .testTag(TestTagsConstants.BUTTON_AMOUNT_TEXT),
                        text = amount,
                        style = SDKTheme.typography.s16Normal.copy(
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .semantics {
                                invisibleToUser()
                            },
                        text = " "
                    )
                    Text(
                        modifier = Modifier
                            .testTag(TestTagsConstants.BUTTON_CURRENCY_TEXT),
                        text = currency,
                        style = SDKTheme.typography.s16Normal.copy(
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        },
        onClick = onClick
    )
    if (showRecurringAgreement)
        RecurringAgreements()
}

@Composable
@Preview
private fun PayButtonDefaultPreview() {
    SDKTheme {
        PayButton(
            payLabel = "Pay",
            amount = "100.00",
            currency = "USD",
            isEnabled = true
        ) {
        }
    }
}

@Composable
@Preview
private fun PayButtonDisabledPreview() {
    SDKTheme {
        PayButton(
            payLabel = "Pay",
            amount = "100.00",
            currency = "USD",
            isEnabled = false
        ) {
        }
    }
}