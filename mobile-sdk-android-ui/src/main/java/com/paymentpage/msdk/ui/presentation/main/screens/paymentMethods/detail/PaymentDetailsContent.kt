package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsContent(
    actionType: SDKActionType,
    paymentIdLabel: String,
    paymentIdValue: String,
    paymentDescriptionLabel: String,
    paymentDescriptionValue: String?,
    merchantAddressLabel: String,
    merchantAddressValue: String?
) {
    Column {
        //Payment ID
        if (actionType != SDKActionType.Verify) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_ID_LABEL_TEXT)
                    .semantics {
                        heading()
                    },
                text = paymentIdLabel,
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_ID_VALUE_TEXT),
                text = paymentIdValue,
                style = SDKTheme.typography.s14Bold.copy(color = Color.White)
            )
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
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.PAYMENT_DESCRIPTION_VALUE_TEXT),
                text = paymentDescriptionValue,
                style = SDKTheme.typography.s14Light.copy(color = Color.White)
            )
        }
        //Address
        if (merchantAddressValue != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.ADDRESS_LABEL_TEXT)
                    .semantics {
                        heading()
                    },
                text = merchantAddressLabel,
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.ADDRESS_VALUE_TEXT),
                text = merchantAddressValue,
                style = SDKTheme.typography.s14Light.copy(color = Color.White)
            )
        }
    }
}