package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun GooglePayButton(isEnabled: Boolean) {
    CustomButton(
        modifier = Modifier
            .height(LocalDimensions.current.googlePayButtonHeight)
            .fillMaxWidth(),
        isEnabled = isEnabled,
        content = {
            Image(
                painter = painterResource(id = R.drawable.googlepay_button_logo),
                contentDescription = null
            )
        },
        color = Color.Black,
        onClick = {}
    )

}