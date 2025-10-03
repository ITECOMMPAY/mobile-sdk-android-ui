package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString


@Composable
internal fun SDKTextWithLink(
    overrideKey: String,
    fontFamily: FontFamily = FontFamily.Default,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val linkRoleContentDescription = stringResource(id = R.string.link_role_content_description)
    val linkedMessage = PaymentActivity
        .stringResourceManager
        .getLinkMessageByKey(overrideKey)

    if (linkedMessage == null) {
        Text(
            text = overrideKey,
            fontFamily = fontFamily,
            style = style
        )
    } else {
        val linkedString = linkedMessage.annotatedString(style)
        val uriHandler = LocalUriHandler.current
        ClickableText(
            modifier = Modifier
                .semantics {
                    contentDescription = "${linkedMessage.message} $linkRoleContentDescription"
                }.testTag("${overrideKey.uppercase()}${TestTagsConstants.POSTFIX_LINK}"),
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