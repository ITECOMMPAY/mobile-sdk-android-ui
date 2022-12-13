package com.paymentpage.ui.msdk.sample.ui.threeDSecure

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
import com.paymentpage.ui.msdk.sample.domain.ui.base.viewUseCase
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.paymentpage.ui.msdk.sample.domain.ui.threeDSecure.ThreeDSecureInfoViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.threeDSecure.ThreeDSecureViewState
import com.paymentpage.ui.msdk.sample.domain.ui.threeDSecure.ThreeDSecureViewUC
import com.paymentpage.ui.msdk.sample.ui.base.ComposeViewState
import com.paymentpage.ui.msdk.sample.ui.threeDSecure.views.ThreeDSecureCheckbox

@Composable
internal fun ThreeDSecureState(
    route: MainHostScreens.ThreeDSecure,
    viewUseCase: ThreeDSecureViewUC = viewUseCase(route.toString(), {
        ThreeDSecureViewUC()
    })
) {
    ComposeViewState(
        viewUseCase = viewUseCase
    ) { viewState, listener ->
        ThreeDSecureScreen(viewState, listener)
    }
}

@Composable
internal fun ThreeDSecureScreen(
    viewState: ThreeDSecureViewState?,
    intentListener: (ThreeDSecureInfoViewIntents) -> Unit
) {
    val jsonThreeDSecureInfo = viewState?.jsonThreeDSecureInfo ?: ""
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.RemoveCustomerAccountInfo)
            }
        ) {
            Text(text = "Remove customer account info", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.RemoveCustomerShipping)
            }
        ) {
            Text(text = "Remove customer shipping", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.RemoveCustomerMpiResult)
            }
        ) {
            Text(text = "Remove customer mpi result", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.RemovePaymentMerchantRisk)
            }
        ) {
            Text(text = "Remove payment merchant risk", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.ResetData)
            }
        ) {
            Text(text = "Reset", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            onClick = {
                intentListener(ThreeDSecureInfoViewIntents.Exit)
            }
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.size(20.dp))
        ThreeDSecureCheckbox(
            isChecked = viewState?.isEnabledThreeDSecure ?: false
        ) {
            intentListener(ThreeDSecureInfoViewIntents.ChangeCheckbox)
        }
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = jsonThreeDSecureInfo,
            onValueChange = {
                intentListener(ThreeDSecureInfoViewIntents.ChangeField(json = it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "3DS Info") }
        )
    }
}
