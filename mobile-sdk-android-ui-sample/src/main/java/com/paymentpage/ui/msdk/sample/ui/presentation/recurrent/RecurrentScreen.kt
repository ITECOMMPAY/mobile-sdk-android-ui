package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.views.RecurrentSchedule
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.views.RecurrentTitle
import java.util.*

@Composable
internal fun RecurrentScreen(
    navController: NavController,
    viewModel: RecurrentViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        null -> viewModel.pushIntent(RecurrentViewIntents.Init)
    }
    val recurrentData = viewState?.recurrentData ?: RecurrentData.defaultData
    val schedules = viewState?.schedules
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        RecurrentTitle()
        OutlinedTextField(
            value = recurrentData.type ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(type = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Type") }
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = recurrentData.expiryDay ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(expiryDay = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry day") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.expiryMonth ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(expiryMonth = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry month") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.expiryYear ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(expiryYear = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Expiry year") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.period ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(period = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Period") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.time ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(time = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Time") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.startTime ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(startTime = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Start time") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.scheduledPaymentID ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(scheduledPaymentID = it)))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Schedule payment ID") }
        )
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = recurrentData.amount?.toString() ?: "",
            onValueChange = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(recurrentData = recurrentData.copy(amount = it.filter { char -> char.isDigit() }
                        .toLongOrNull())
                    )))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.size(20.dp))

        schedules?.forEachIndexed { index, schedule ->
            RecurrentSchedule(
                title = "Schedule №${index + 1}",
                index = index,
                date = schedule.date,
                amount = schedule.amount
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                val changed = viewState?.schedules?.toMutableList() ?: mutableListOf()
                changed.add(RecurrentDataSchedule())
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(schedules = changed)
                ))
            }
        ) {
            Text(text = "Add schedule", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(
                        recurrentData = RecurrentData(
                            type = "R",
                            expiryDay = "06",
                            expiryMonth = "11",
                            expiryYear = "2026",
                            period = "M",
                            time = "12:00:00",
                            startTime = "12-10-2022",
                            scheduledPaymentID = "sdk_recurrent_${UUID.randomUUID().toString().take(8)}",
                            amount = 1000
                        ),
                        schedules = schedules?.map {
                            RecurrentDataSchedule(
                                date = "10-08-202${(0..9).random()}",
                                amount = (1000..2000).random().toLong()
                            )
                        } ?: listOf(RecurrentDataSchedule(
                            date = "10-08-202${(0..9).random()}",
                            amount = (1000..2000).random().toLong())
                        ))
                ))
            }
        ) {
            Text(text = "Fill mock data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(
                        recurrentData = RecurrentData.defaultData,
                        schedules = null
                    )
                ))
            }
        ) {
            Text(text = "Reset data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
    }
}