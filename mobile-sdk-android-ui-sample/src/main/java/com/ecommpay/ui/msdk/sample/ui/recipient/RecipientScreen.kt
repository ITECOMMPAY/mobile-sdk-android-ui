package com.ecommpay.ui.msdk.sample.ui.recipient

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
import com.ecommpay.ui.msdk.sample.domain.ui.base.viewUseCase
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.recipient.RecipientViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.recipient.RecipientViewState
import com.ecommpay.ui.msdk.sample.domain.ui.recipient.RecipientViewUC
import com.ecommpay.ui.msdk.sample.ui.base.ComposeViewState
import com.ecommpay.ui.msdk.sample.ui.recipient.views.RecipientCheckbox
import com.ecommpay.ui.msdk.sample.ui.recipient.views.RecipientTitle

@Composable
internal fun RecipientState(
    route: MainHostScreens.Recipient,
    viewUseCase: RecipientViewUC = viewUseCase(route.toString()) {
        RecipientViewUC()
    },
) {
    ComposeViewState(viewUseCase = viewUseCase) { viewState, intentListener ->
        RecipientScreen(
            viewState = viewState,
            intentListener = intentListener
        )
    }
}

@Composable
internal fun RecipientScreen(
    viewState: RecipientViewState,
    intentListener: (RecipientViewIntents) -> Unit,
) {
    val recipientData = viewState.recipientData
    val padding = 10.dp
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        RecipientTitle()
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.walletOwner ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(walletOwner = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Wallet owner") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.walletId ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(walletId = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Wallet id") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.country ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(country = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Country") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.pan ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(pan = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Pan") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.cardHolder ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(cardHolder = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Card holder") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.address ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(address = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Address") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.city ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(city = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "City") }
        )
        Spacer(modifier = Modifier.size(padding))
        OutlinedTextField(
            value = recipientData.stateCode ?: "",
            onValueChange = {
                intentListener(
                    RecipientViewIntents.ChangeField(
                        recipientData = recipientData.copy(stateCode = it))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "State code") }
        )
        Spacer(modifier = Modifier.size(padding))
        RecipientCheckbox(
            isChecked = viewState.isEnabledRecipient,
            listener = intentListener
        )
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecipientViewIntents.FillMockData)
            }
        ) {
            Text(text = "Fill mock data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecipientViewIntents.ResetData)
            }
        ) {
            Text(text = "Reset Data", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(padding))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(RecipientViewIntents.Exit)
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
    }
}
