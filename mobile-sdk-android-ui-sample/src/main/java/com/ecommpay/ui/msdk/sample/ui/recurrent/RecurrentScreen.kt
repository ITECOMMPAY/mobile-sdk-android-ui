package com.ecommpay.ui.msdk.sample.ui.recurrent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentDataSchedule
import com.ecommpay.ui.msdk.sample.domain.ui.base.viewUseCase
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewState
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewUC
import com.ecommpay.ui.msdk.sample.ui.base.ComposeViewState
import com.ecommpay.ui.msdk.sample.ui.recurrent.views.RecurrentCheckbox
import com.ecommpay.ui.msdk.sample.ui.recurrent.views.RecurrentSchedule
import com.ecommpay.ui.msdk.sample.ui.recurrent.views.RecurrentTitle

@Composable
internal fun RecurrentState(
    route: MainHostScreens.Recurrent,
    viewUseCase: RecurrentViewUC = viewUseCase(route.toString(), {
        RecurrentViewUC()
    })
) {
    ComposeViewState(
        viewUseCase = viewUseCase) { viewState, intentListener ->
        RecurrentScreen(
            viewState = viewState,
            intentListener = intentListener
        )
    }
}


@Composable
internal fun RecurrentScreen(
    viewState: RecurrentViewState,
    intentListener: (RecurrentViewIntents) -> Unit,
) {
    val recurrentData = viewState.recurrentData
    val schedules = viewState.recurrentData.schedule
    val padding = 10.dp
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        RecurrentTitle()
        Spacer(modifier = Modifier.size(padding))
        Text(text = "register: ${recurrentData.register}")
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.type ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(type = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Type") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.expiryDay ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(expiryDay = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry day") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.expiryMonth ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(expiryMonth = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry month") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.expiryYear ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(expiryYear = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry year") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.period ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(period = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Period") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.time ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(time = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Time") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.startDate ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(startDate = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Start time") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.scheduledPaymentID ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(
                        scheduledPaymentID = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Schedule payment ID") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recurrentData.amount?.toString() ?: "",
            onValueChange = {
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(amount = it.filter { char -> char.isDigit() }
                        .toLongOrNull())
                    ))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.size(padding))
        schedules?.forEachIndexed { index, schedule ->
            RecurrentSchedule(
                viewState = viewState,
                listener = intentListener,
                title = "Schedule №${index + 1}",
                index = index,
                date = schedule.date,
                amount = schedule.amount
            )
            Spacer(modifier = Modifier.size(padding))
        }
        RecurrentCheckbox(
            isChecked = viewState.isEnabledRecurrent,
            listener = intentListener
        )
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                val changed = viewState.recurrentData.schedule?.toMutableList() ?: mutableListOf()
                changed.add(RecurrentDataSchedule())
                intentListener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = recurrentData.copy(schedule = changed)
                ))
            }
        ) {
            Text(text = "Add schedule", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecurrentViewIntents.FillMockData)
            }
        ) {
            Text(text = "Fill mock data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecurrentViewIntents.ResetData)
            }
        ) {
            Text(text = "Reset data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecurrentViewIntents.Exit)
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
    }
}