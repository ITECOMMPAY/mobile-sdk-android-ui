package com.ecommpay.ui.msdk.sample.ui.main.views.screenDisplayMode

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.EcmpScreenDisplayMode
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewState
import com.ecommpay.ui.msdk.sample.ui.components.SDKCheckbox


@Composable
internal fun SelectScreenDisplayMode(
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    EcmpScreenDisplayMode.values()
        .forEach { screenDisplayMode ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SDKCheckbox(
                    modifier = Modifier
                        .testTag(
                            "${
                                screenDisplayMode.name.lowercase().replace("_", "")
                            }Checkbox"
                        ),
                    text = screenDisplayMode.name,
                    isChecked = viewState.selectedScreenDisplayModes.contains(screenDisplayMode),
                    onCheckedChange = {
                        intentListener(
                            MainViewIntents.SelectScreenDisplayMode(
                                screenDisplayMode = screenDisplayMode
                            )
                        )
                    }
                )
            }
        }
    Spacer(modifier = Modifier.size(10.dp))
}
