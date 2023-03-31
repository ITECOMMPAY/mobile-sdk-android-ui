package com.ecommpay.ui.msdk.sample.ui.recurrent.views

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewState

@Composable
internal fun RecurrentSchedule(
    viewState: RecurrentViewState,
    listener: (RecurrentViewIntents) -> Unit,
    title: String,
    date: String?,
    amount: Long?,
    index: Int,
) {
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
                modifier = Modifier
                    .weight(1f)
                    .testTag("recurrentScheduleTitle${index}Text"),
                text = "${title}${index + 1}"
            )
            IconButton(
                modifier = Modifier
                    .testTag("recurrentDeleteSchedule${index}Button"),
                onClick = {
                    val changed = viewState.recurrentData.schedule?.toMutableList()
                    changed?.removeAt(index)
                    listener(
                        RecurrentViewIntents.ChangeField(
                        recurrentData = viewState.recurrentData.copy(schedule = changed)
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
            modifier = Modifier
                .fillMaxWidth()
                .testTag("recurrentScheduleDate${index}TextField"),
            value = date ?: "",
            onValueChange = {
                val changed = viewState.recurrentData.schedule?.toMutableList()
                changed?.set(index,
                    viewState.recurrentData.schedule[index].copy(date = it))
                listener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = RecurrentData(schedule = changed)
                ))
            },
            label = { Text(text = "Date") }
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("recurrentScheduleAmount${index}TextField"),
            value = amount?.toString() ?: "",
            onValueChange = {
                val changed = viewState.recurrentData.schedule?.toMutableList()
                changed?.set(index,
                    viewState.recurrentData.schedule[index].copy(amount = it.toLongOrNull()))
                listener(
                    RecurrentViewIntents.ChangeField(
                    recurrentData = RecurrentData(schedule = changed)
                ))
            },
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}