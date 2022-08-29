package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString


@Composable
internal fun SDKTextWithLink(
    overrideKey: String,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val linkedMessage = PaymentActivity
        .stringResourceManager
        .getLinkMessageByKey(overrideKey)

    if (linkedMessage == null) {
        Text(text = overrideKey)
    } else {
        val linkedString = linkedMessage.annotatedString()
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
                        if (stringAnnotation.item.isNotBlank() && stringAnnotation.item.isNotEmpty())
                            uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    }
}