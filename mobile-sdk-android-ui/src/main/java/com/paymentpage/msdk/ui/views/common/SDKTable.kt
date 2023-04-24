package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.paymentpage.msdk.ui.TestTagsConstants

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SDKTable(
    tableMap: Map<String?, String?>,
    labelTextStyle: TextStyle,
    valueTextStyle: TextStyle = labelTextStyle,
    spaceBetweenItems: Dp,
    isTableEmptyCallback: ((Boolean) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val nullSafetyMap = tableMap
            .filterValues { !it.isNullOrEmpty() }
            .mapValues { it.value as String }
            .filterKeys { !it.isNullOrEmpty() }
            .mapKeys { it.key as String }

        if (isTableEmptyCallback != null) {
            isTableEmptyCallback(nullSafetyMap.isEmpty())
        }

        nullSafetyMap
            .forEach { (key, value) ->
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .semantics {
                                heading()
                            }
                            .testTag(
                                "${
                                    key.uppercase()
                                }${
                                    TestTagsConstants.POSTFIX_LABEL
                                }${
                                    TestTagsConstants.POSTFIX_TEXT
                                }"
                            ),
                        text = key,
                        style = labelTextStyle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier
                            .semantics {
                                invisibleToUser()
                            },
                        text = " "
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .testTag(
                                "${
                                    value.uppercase()
                                }${
                                    TestTagsConstants.POSTFIX_VALUE
                                }${
                                    TestTagsConstants.POSTFIX_TEXT
                                }"
                            ),
                        text = value,
                        style = valueTextStyle,
                        textAlign = TextAlign.End
                    )
                }
                if (key != nullSafetyMap.keys.last() || value != nullSafetyMap.values.last())
                    Spacer(modifier = Modifier.height(spaceBetweenItems))
            }
    }
}