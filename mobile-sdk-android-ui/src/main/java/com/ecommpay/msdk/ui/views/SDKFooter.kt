package com.ecommpay.msdk.ui.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun SDKFooter(
    @DrawableRes iconLogo: Int,
    poweredByText: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = poweredByText,
            style = SDKTheme.typography.s12Light
                .copy(
                    color = SDKTheme.colors.footerContent,
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