package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.utils.CollectAsEffect

@Composable
internal fun AdditionalFieldScreen(
    navController: NavController,
    viewModel: AdditionalFieldsViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        null -> viewModel.pushIntent(AdditionalFieldsViewIntents.Init)
    }

    viewModel.viewAction.CollectAsEffect { viewAction ->
        when (viewAction) {
            is NavRoutes -> navController.navigate(viewAction.route)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewState?.additionalFields?.forEachIndexed { index, additionalField ->
            OutlinedTextField(
                value = additionalField.value ?: "",
                onValueChange = { str ->
                    val changed = viewState?.additionalFields?.toMutableList()
                    changed?.set(index, SDKAdditionalField(additionalField.type, str))
                    viewModel.pushIntent(
                        AdditionalFieldsViewIntents.ChangeField(
                            viewState?.copy(
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
                navController.popBackStack()
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}