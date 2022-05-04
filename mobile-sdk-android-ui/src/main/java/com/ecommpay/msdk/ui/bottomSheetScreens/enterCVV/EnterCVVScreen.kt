package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewStates

@Composable
fun EnterCVVScreen(
    state: ViewStates<EnterCVVViewData>?,
    intentListener: (intent: EnterCVVViewIntents) -> Unit,
) {
    when (state) {
        is DefaultViewStates.Display -> {
            Box(contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.3f))
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(MaterialTheme.colors.surface),
                ) {
                    Text(text = state.viewData.id)
                }
            }
        }
    }
}