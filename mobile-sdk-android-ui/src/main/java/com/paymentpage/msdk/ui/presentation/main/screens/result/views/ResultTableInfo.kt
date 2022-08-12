package com.paymentpage.msdk.ui.presentation.main.screens.result.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun ResultTableInfo(
    titleKeyWithValueMap: Map<String?, String?>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = SDKTheme.colors.panelBackgroundColor)
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
    ) {
        titleKeyWithValueMap
            .filterValues { it.isNullOrEmpty() }
            .mapValues { it.value as String }
            .filterKeys { it.isNullOrEmpty() }
            .mapKeys { it.key as String }
            .forEach { (key, value) ->
                Row {
                    Column {
                        Text(
                            text = key,
                            style = SDKTheme.typography.s14Light.copy(color = SDKTheme.colors.secondaryTextColor),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Text(text = " ")
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = End) {
                        Text(
                            text = value,
                            style = SDKTheme.typography.s14Normal,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
    }
}