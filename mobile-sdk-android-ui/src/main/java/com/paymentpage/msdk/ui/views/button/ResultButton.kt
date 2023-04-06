package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun ResultButton(
    modifier: Modifier = Modifier,
    resultLabel: String,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        content = {
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.BUTTON_LABEL_TEXT),
                text = resultLabel,
                style = SDKTheme.typography.s16Normal.copy(color = Color.White)
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