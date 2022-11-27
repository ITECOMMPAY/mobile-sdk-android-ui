package com.paymentpage.ui.msdk.sample.ui.additionalFields

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.ui.msdk.sample.domain.ui.additionalFields.AdditionalFieldsViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.additionalFields.AdditionalFieldsViewState
import com.paymentpage.ui.msdk.sample.domain.ui.additionalFields.AdditionalFieldsViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.base.viewUseCase
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.paymentpage.ui.msdk.sample.ui.base.ComposeViewState


@Composable
internal fun AdditionalFieldsState(
    route: MainHostScreens.AdditionalFields,
    viewUseCase: AdditionalFieldsViewUC = viewUseCase(
        route.toString(),
        {
            AdditionalFieldsViewUC()
        })
) {
    ComposeViewState(
        viewUseCase = viewUseCase
    ) { viewState, intentListener ->
        AdditionalFieldScreen(
            viewState = viewState,
            intentListener = intentListener
        )
    }
}


@Composable
internal fun AdditionalFieldScreen(
    viewState: AdditionalFieldsViewState,
    intentListener: (AdditionalFieldsViewIntents) -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewState.additionalFields.forEachIndexed { index, additionalField ->
            OutlinedTextField(
                value = additionalField.value ?: "",
                onValueChange = { str ->
                    val changed = viewState.additionalFields.toMutableList()
                    changed[index] = SDKAdditionalField(additionalField.type, str)
                    intentListener(
                        AdditionalFieldsViewIntents.ChangeField(
                            viewState.copy(
                                additionalFields = changed
                            )
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = additionalField.type?.name ?: "") }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(AdditionalFieldsViewIntents.Exit)
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}