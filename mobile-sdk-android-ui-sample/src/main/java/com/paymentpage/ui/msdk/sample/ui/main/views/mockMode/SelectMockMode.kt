package com.paymentpage.ui.msdk.sample.ui.main.views.mockMode

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.SDKMockModeType
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewState

@Composable
internal fun SelectMockMode(
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    SDKMockModeType.values()
        .forEachIndexed { index, mockModeType ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == viewState.selectedMockModeTypeId,
                    onClick = {
                        intentListener(
                            MainViewIntents.SelectMockMode(
                                id = index,
                                paymentData = viewState.paymentData.copy(mockModeType = mockModeType)
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = mockModeType.name)
            }
        }
    Spacer(modifier = Modifier.size(10.dp))
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClick = {
            intentListener(
                MainViewIntents.SelectMockMode(
                    id = 0,
                    paymentData = viewState.paymentData.copy(mockModeType = SDKMockModeType.DISABLED)
                )
            )
        }) {
        Text(text = "Reset mock mode type", color = Color.White, fontSize = 18.sp)
    }
    Spacer(modifier = Modifier.size(10.dp))
}