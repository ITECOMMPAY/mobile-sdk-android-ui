package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun SDKFooter(
    isVisiblePrivacyPolicy: Boolean = true,
    isVisibleCookiePolicy: Boolean = true,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isVisiblePrivacyPolicy) {
            SDKTextWithLink(
                overrideKey = OverridesKeys.TITLE_PRIVACY_POLICY,
                style = SDKTheme.typography.s12Light.copy(textAlign = TextAlign.End),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isVisibleCookiePolicy && isVisiblePrivacyPolicy) {
            Spacer(modifier = Modifier.size(15.dp))
            Box(
                modifier = Modifier.clip(CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(SDKTheme.colors.footerTextColor)
                )
            }
        }
        if (isVisibleCookiePolicy) {
            Spacer(modifier = Modifier.size(15.dp))
            SDKTextWithLink(
                overrideKey = "cookie_policy",
                style = SDKTheme.typography.s12Light.copy(textAlign = TextAlign.Start),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    if (isVisibleCookiePolicy || isVisiblePrivacyPolicy)
        Spacer(modifier = Modifier.size(15.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = LocalPaymentOptions.current.footerLabel ?: "",
            style = SDKTheme.typography.s12Light
                .copy(
                    color = SDKTheme.colors.footerTextColor,
                    fontStyle = FontStyle.Italic
                )
        )
        Text(text = " ")
        LocalPaymentOptions.current.footerImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun FooterPreview() {
    SDKTheme {
        SDKFooter()
    }
}