package com.paymentpage.msdk.ui.views.button

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .border(
                width = 1.dp,
                color = if (SDKTheme.colors.isDarkTheme) Color(0xFF767676) else Color(0xFF3D4043),
                shape = RoundedCornerShape(5.dp)
            )
            .testTag(TestTagsConstants.GOOGLE_PAY_BUTTON),
        isEnabled = isEnabled,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = "Pay with",
                    style = SDKTheme.typography.s16Normal.copy(
                        color = if (!SDKTheme.colors.isDarkTheme)
                            Color.White
                        else
                            Color(0xFF1F1F1F),
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.productsans)),
                    ),
                    fontFamily = FontFamily(Font(R.font.productsans)),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = SDKTheme.images.googlePayLogo),
                    contentDescription = stringResource(id = R.string.icon_google_pay_content_description)
                )
            }
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

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GooglePayButtonPreviewDark() {
    GooglePayButton(
        isEnabled = true,
        onClick = {}
    )
}
