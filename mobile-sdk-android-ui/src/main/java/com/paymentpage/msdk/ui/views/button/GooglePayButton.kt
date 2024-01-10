package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun GooglePayButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .height(LocalDimensions.current.googlePayButtonHeight)
            .fillMaxWidth()
            .testTag(TestTagsConstants.GOOGLE_PAY_BUTTON),
        isEnabled = isEnabled,
        content = {
            Image(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = if (SDKTheme.colors.isDarkTheme) R.drawable.pay_with_googlepay_button_content_dark else R.drawable.pay_with_googlepay_button_content),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )

        },
        color = if (SDKTheme.colors.isDarkTheme)
            Color.White
        else
            Color(0xFF1F1F1F),
        onClick = onClick
    )
}

@Composable
@Preview
fun GooglePayButtonPreview() {
    GooglePayButton(
        isEnabled = true,
        onClick = {}
    )
}