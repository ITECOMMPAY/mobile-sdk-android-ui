package com.ecommpay.msdk.ui.utils.extensions.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.ecommpay.msdk.core.domain.entities.init.translation.LinkMessage
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun LinkMessage.annotatedString(): AnnotatedString = buildAnnotatedString {
    val resultMessage = message
    if (resultMessage.isNullOrEmpty()) return@buildAnnotatedString
    append(resultMessage)

    links?.forEach { link ->
        val linkMessage = link.message
        val linkUrl = link.url
        if (!linkMessage.isNullOrEmpty() && !linkUrl.isNullOrEmpty()) {
            val startIndex = linkMessage.indexOf(linkMessage)
            val endIndex = startIndex + linkMessage.length

            addStyle(
                style = SpanStyle(
                    color = SDKTheme.colors.brand,
                    textDecoration = TextDecoration.Underline
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = linkUrl,
                start = startIndex,
                end = endIndex
            )
        }
    }


}