package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.RecurrentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.RecurrentViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule

@Composable
internal fun RecurrentSchedule(
    viewModel: RecurrentViewModel = viewModel(),
    title: String,
    date: String?,
    amount: Long?,
    index: Int,
) {
    val viewState by viewModel.viewState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title
            )
            IconButton(
                onClick = {
                    val changed = viewState?.schedules?.toMutableList()
                    changed?.removeAt(index)
                    viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                        viewData = viewState?.copy(schedules = changed)
                    ))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
        OutlinedTextField(
            value = date ?: "",
            onValueChange = {
                val changed = viewState?.schedules?.toMutableList()
                changed?.set(index,
                    viewState?.schedules?.get(index)?.copy(date = it) ?: RecurrentDataSchedule())
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(schedules = changed)
                ))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Date") }
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = amount?.toString() ?: "",
            onValueChange = {
                val changed = viewState?.schedules?.toMutableList()
                changed?.set(index,
                    viewState?.schedules?.get(index)?.copy(amount = it.toLongOrNull())
                        ?: RecurrentDataSchedule())
                viewModel.pushIntent(RecurrentViewIntents.ChangeField(
                    viewData = viewState?.copy(schedules = changed)
                ))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}