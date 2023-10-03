package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "Pay with",
                    style = SDKTheme.typography.s16Normal.copy(color = if (!SDKTheme.colors.isDarkTheme) Color.White else Color.Black )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = SDKTheme.images.googlePayLogo),
                    contentDescription = stringResource(id = R.string.icon_google_pay_content_description)
                )
            }
        },
        color = SDKTheme.colors.neutral,
        onClick = onClick
    )
}