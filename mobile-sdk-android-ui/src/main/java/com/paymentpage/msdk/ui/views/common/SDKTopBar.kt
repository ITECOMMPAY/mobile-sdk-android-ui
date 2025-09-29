package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
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
            .wrapContentSize()
            .semantics {
                contentDescription = title ?: ""
                isTraversalGroup = true
                traversalIndex = -1f
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .semantics {
                    heading()
                }
                .testTag(TestTagsConstants.SCREEN_TITLE_TEXT),
            maxLines = 1,
            fontFamily = SohneBreitFamily,
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
                    )
                    .semantics {
                        role = Role.Button
                    }
                    .testTag(TestTagsConstants.SCREEN_BACK_BUTTON),
                imageVector = Icons.Default.ArrowBack,
                colorFilter = ColorFilter.tint(
                    color = customColor(brandColor = paymentOptions.primaryBrandColor)
                ),
                contentDescription = stringResource(id = R.string.icon_back_content_description),
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
                    )
                    .semantics {
                        role = Role.Button
                    }
                    .testTag(TestTagsConstants.SCREEN_CLOSE_BUTTON),
                imageVector = Icons.Default.Close,
                colorFilter = ColorFilter.tint(
                    color = SDKTheme.colors.neutral
                ),
                contentDescription = stringResource(id = R.string.icon_close_content_description),
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

