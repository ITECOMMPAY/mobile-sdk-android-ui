package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKColorButton
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
import com.paymentpage.msdk.ui.theme.defaults.SdkColorDefaults
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun ResultButton(
    modifier: Modifier = Modifier,
    resultLabel: String,
    color: SDKColorButton = SdkColorDefaults.buttonColor(),
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        color = color,
        content = {
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.BUTTON_LABEL_TEXT),
                text = resultLabel,
                fontFamily = SohneBreitFamily,
                style = SDKTheme.typography.s14SemiBold.copy(color = color.text().value)
            )
        },
        isEnabled = true,
        onClick = onClick
    )
}

@Composable
@Preview
private fun ResultButtonDefaultPreview() {
    SDKTheme {
        ResultButton(
            resultLabel = "Return to app",
            onClick = {}
        )
    }
}