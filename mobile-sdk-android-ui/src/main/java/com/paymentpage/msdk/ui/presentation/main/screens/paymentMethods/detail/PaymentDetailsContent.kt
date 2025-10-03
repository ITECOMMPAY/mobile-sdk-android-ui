package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.copyInClipBoard

@Composable
fun PaymentDetailsContent(
    paymentIdLabel: String,
    paymentIdValue: String?,
    paymentDescriptionLabel: String,
    paymentDescriptionValue: String?,
) {
    Column {
        //Payment ID
        if (paymentIdValue != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_ID_LABEL_TEXT)
                    .semantics {
                        heading()
                    },
                text = paymentIdLabel,
                style = SDKTheme.typography.s12Light.copy(color = SDKTheme.colors.textPrimaryInverted.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))

            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .testTag(TestTagsConstants.PAYMENT_ID_VALUE_TEXT)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    text = paymentIdValue,
                    style = SDKTheme.typography.s14Bold.copy(color = SDKTheme.colors.textPrimaryInverted),
                )

                val currentContext = LocalContext.current

                Image(
                    painter = painterResource(R.drawable.ic_clipboard_copy),
                    contentDescription = "Copy payment id",
                    colorFilter = ColorFilter.tint(SDKTheme.colors.textPrimaryInverted),
                    modifier = Modifier.clickable {
                        currentContext.copyInClipBoard(text = paymentIdValue)
                    },
                    alignment = Alignment.Center,
                )
            }
        }
        //Description
        if (paymentDescriptionValue != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_DESCRIPTION_LABEL_TEXT)
                    .semantics {
                        heading()
                    },
                text = paymentDescriptionLabel,
                style = SDKTheme.typography.s12Normal.copy(color = SDKTheme.colors.textPrimaryInverted.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_DESCRIPTION_VALUE_TEXT),
                text = paymentDescriptionValue,
                style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.textPrimaryInverted)
            )
        }
    }
}