package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow


@Composable
internal fun SDKTextWithLink(
    linkedString: AnnotatedString,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val uriHandler = LocalUriHandler.current
    ClickableText(
        style = style,
        text = linkedString,
        maxLines = maxLines,
        overflow = overflow,
        onClick = {
            linkedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    if (stringAnnotation.item.isNotBlank() &&
                        stringAnnotation.item.isNotEmpty()
                    )
                        uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}