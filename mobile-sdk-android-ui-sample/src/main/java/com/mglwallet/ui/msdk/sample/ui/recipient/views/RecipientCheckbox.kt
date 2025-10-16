package com.mglwallet.ui.msdk.sample.ui.recipient.views

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
import com.mglwallet.ui.msdk.sample.domain.ui.recipient.RecipientViewIntents

@Composable
internal fun RecipientCheckbox(
    isChecked: Boolean,
    listener: (RecipientViewIntents) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                listener(RecipientViewIntents.ChangeCheckbox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                listener(RecipientViewIntents.ChangeCheckbox)
            },
        )
        Text(text = "Submit Recipient Info")
    }
}