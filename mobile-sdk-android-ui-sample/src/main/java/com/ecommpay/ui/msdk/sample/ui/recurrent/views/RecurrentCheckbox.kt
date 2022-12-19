package com.ecommpay.ui.msdk.sample.ui.recurrent.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ecommpay.ui.msdk.sample.domain.ui.recurrent.RecurrentViewIntents

@Composable
internal fun RecurrentCheckbox(
    isChecked: Boolean,
    listener: (RecurrentViewIntents) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                listener(RecurrentViewIntents.ChangeCheckbox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                listener(RecurrentViewIntents.ChangeCheckbox)
            },
        )
        Text(text = "Submit Recurrent Info")
    }
}