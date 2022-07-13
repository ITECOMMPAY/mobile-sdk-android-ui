package com.ecommpay.msdk.ui.views.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun Footer(
    @DrawableRes iconLogo: Int,
    poweredByText: String,
    privacyPolicy: AnnotatedString? = null
) {
    val uriHandler = LocalUriHandler.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        privacyPolicy?.let { linkedString ->
            ClickableText(
                style = SDKTheme.typography.s12Light,
                text = linkedString,
                onClick = {
                    linkedString
                        .getStringAnnotations("URL", it, it)
                        .firstOrNull()?.let { stringAnnotation ->
                            uriHandler.openUri(stringAnnotation.item)
                        }
                }
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding20))
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
}

@Composable
@Preview(showBackground = true)
internal fun FooterPreview() {
    SDKTheme {
        Footer(
            iconLogo = SDKTheme.images.sdkLogoResId,
            poweredByText = stringResource(id = R.string.powered_by_label)
        )
    }
}