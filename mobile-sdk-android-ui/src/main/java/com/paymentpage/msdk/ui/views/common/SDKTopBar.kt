package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.customColor


@Composable
internal fun SDKTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showCloseButton: Boolean = true,
    onClose: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
) {
    val paymentOptions = LocalPaymentOptions.current
    Row(
        modifier
            .fillMaxWidth()
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            maxLines = 1,
            style = SDKTheme.typography.s22Bold,
            text = title ?: "",
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(5.dp))
        if (onBack != null) {
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .clickable(
                        indication = null, //turn off animation by click
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onBack() }
                    ),
                imageVector = Icons.Default.ArrowBack,
                colorFilter = ColorFilter.tint(
                    color = customColor(brandColor = paymentOptions.brandColor)
                ),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        if (onClose != null && showCloseButton)
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onClose() }
                    ),
                imageVector = Icons.Default.Close,
                colorFilter = ColorFilter.tint(
                    color = customColor(brandColor = paymentOptions.brandColor)
                ),
                contentDescription = null,
            )
    }

}

@Composable
@Preview
internal fun PreviewLightToolbar() {
    SDKTheme {
        SDKTopBar(title = "Payment Methods", onClose = {})
    }
}

@Composable
@Preview
internal fun PreviewToolbarWithLongTitle() {
    SDKTheme {
        SDKTopBar(
            title = "Very ver very Very ver very Very ver very Very ver very long title",
            onClose = {},
            onBack = {}
        )
    }
}

