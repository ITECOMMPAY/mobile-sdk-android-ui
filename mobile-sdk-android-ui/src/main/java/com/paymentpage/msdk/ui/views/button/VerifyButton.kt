package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun VerifyButton(
    modifier: Modifier = Modifier,
    verifyLabel: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        isEnabled = isEnabled,
        content = {
            Text(
                text = verifyLabel,
                style = SDKTheme.typography.s16Normal.copy(color = Color.White)
            )
        },
        onClick = onClick
    )
}