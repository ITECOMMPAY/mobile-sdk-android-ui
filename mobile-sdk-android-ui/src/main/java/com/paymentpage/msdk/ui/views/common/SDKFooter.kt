package com.paymentpage.msdk.ui.views.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString

@Composable
internal fun SDKFooter(
    @DrawableRes iconLogo: Int,
    poweredByText: String,
    isVisiblePrivacyPolicy: Boolean = true,
    isVisibleCookiePolicy: Boolean = true,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        if (isVisiblePrivacyPolicy) {
            val privacyPolicy = PaymentActivity
                .stringResourceManager
                .getLinkMessageByKey("privacy_policy")
                .annotatedString()
            SDKTextWithLink(
                linkedString = privacyPolicy,
                style = SDKTheme.typography.s12Light.copy(textAlign = TextAlign.End),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isVisibleCookiePolicy) {
            val cookiePolicy = PaymentActivity
                .stringResourceManager
                .getLinkMessageByKey("cookie_policy")
                .annotatedString()
            Spacer(modifier = Modifier.size(15.dp))
            SDKTextWithLink(
                linkedString = cookiePolicy,
                style = SDKTheme.typography.s12Light.copy(textAlign = TextAlign.Start),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = poweredByText,
            style = SDKTheme.typography.s12Light
                .copy(
                    color = SDKTheme.colors.footerTextColor,
                    fontStyle = FontStyle.Italic
                )
        )
        Text(text = " ")
        Image(
            painter = painterResource(id = iconLogo),
            contentDescription = ""
        )
    }

}

@Composable
@Preview(showBackground = true)
internal fun FooterPreview() {
    SDKTheme {
        SDKFooter(
            iconLogo = SDKTheme.images.sdkLogoResId,
            poweredByText = stringResource(id = R.string.powered_by_label)
        )
    }
}