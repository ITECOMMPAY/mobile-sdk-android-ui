package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.paymentpage.msdk.ui.R
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
            .fillMaxWidth(),
        isEnabled = isEnabled,
        content = {
            Image(
                modifier = Modifier,
                painter = painterResource(id = SDKTheme.images.googlePayLogo),
                contentDescription = stringResource(id = R.string.icon_google_pay_content_description)
            )
        },
        color = SDKTheme.colors.neutral,
        onClick = onClick
    )
}