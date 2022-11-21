package com.paymentpage.ui.msdk.sample.ui.presentation.main

import androidx.compose.foundation.border
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ComposeViewState
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.*
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.CustomizationCheckbox
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customBrandColor.BrandColorPicker
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customLogo.SelectImagesList
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.mockMode.MockModeCheckbox
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.mockMode.SelectMockMode

@Composable
internal fun MainState(
    viewModel: MainViewModel = viewModel(),
    navController: NavController,
    mainViewActionListener: (MainViewActions) -> Unit,
) {
    ComposeViewState(
        navController = navController,
        viewModel = viewModel,
        mainViewActionListener = mainViewActionListener
    ) { viewState, intentListener ->
        MainScreen(
            viewState = viewState,
            intentListener = intentListener
        )
    }
}

@Composable
fun MainScreen(
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    val paymentData = viewState.paymentData
    val padding = 10.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        VersionInfo()
        Spacer(modifier = Modifier.size(padding))
        ProjectSettings(paymentData = paymentData) { intentListener(it) }
        Spacer(modifier = Modifier.size(padding))
        PaymentFields(paymentData = paymentData) { intentListener(it) }
        Spacer(modifier = Modifier.size(padding))
        AdditionalFieldsButton() { intentListener(MainViewIntents.AdditionalFields) }
        Spacer(modifier = Modifier.size(padding))
        RecurrentButton() { intentListener(MainViewIntents.Recurrent) }
        Spacer(modifier = Modifier.size(padding))
        ThreeDSecureButton() { intentListener(MainViewIntents.ThreeDSecure) }
        Spacer(modifier = Modifier.size(padding))
        HideSavedWalletsCheckbox(paymentData = paymentData) { intentListener(it) }
        Spacer(modifier = Modifier.size(padding))
        CustomizationCheckbox(viewState.isVisibleCustomizationFields) {
            intentListener(MainViewIntents.ChangeCustomizationCheckbox)
        }
        if (viewState.isVisibleCustomizationFields) {
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, Color.LightGray)
                    .padding(horizontal = 10.dp),
                content = {
                    Spacer(modifier = Modifier.size(10.dp))
                    BrandColorPicker(paymentData = paymentData) { intentListener(it) }
                    Spacer(modifier = Modifier.size(10.dp))
                    SelectImagesList(
                        selectedResourceImageId = viewState.selectedResourceImageId,
                        paymentData = paymentData
                    ) { intentListener(it) }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            )
        }
        Spacer(modifier = Modifier.size(padding))
        ApiHostCheckbox(viewState.isVisibleApiHostFields) {
            intentListener(MainViewIntents.ChangeApiHostCheckBox)
        }
        if (viewState.isVisibleApiHostFields) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.LightGray)
                .padding(horizontal = 10.dp)
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = paymentData.apiHost,
                    onValueChange = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(apiHost = it)))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Api Host") }
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = paymentData.wsApiHost,
                    onValueChange = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(wsApiHost = it)))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Ws Api Host") }
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(
                                apiHost = PaymentData.defaultPaymentData.apiHost,
                                wsApiHost = PaymentData.defaultPaymentData.wsApiHost
                            )
                        ))
                    }
                ) {
                    Text(
                        text = "Reset changes to default",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Spacer(modifier = Modifier.size(padding))
        GooglePayCheckbox(viewState.isVisibleGooglePayFields) {
            intentListener(MainViewIntents.ChangeGooglePayCheckBox)
        }
        if (viewState.isVisibleGooglePayFields) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.LightGray)
                .padding(horizontal = 10.dp)
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = paymentData.merchantId,
                    onValueChange = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(merchantId = it)))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Merchant Id") }
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = paymentData.merchantName,
                    onValueChange = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(merchantName = it)))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Merchant Name") }
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(
                                merchantId = PaymentData.defaultPaymentData.merchantId,
                                merchantName = PaymentData.defaultPaymentData.merchantName
                            )
                        ))
                    }
                ) {
                    Text(
                        text = "Reset changes to default",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Spacer(modifier = Modifier.size(padding))
        MockModeCheckbox(viewState.isVisibleMockModeType) {
            intentListener(MainViewIntents.ChangeMockModeCheckbox)
        }
        if (viewState.isVisibleMockModeType) {
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.LightGray)
                    .padding(horizontal = 10.dp),
                content = {
                    Spacer(modifier = Modifier.size(10.dp))
                    SelectMockMode()
                    Spacer(modifier = Modifier.size(10.dp))
                }
            )
        }
        Spacer(modifier = Modifier.size(padding))
        SaleButton() { intentListener(MainViewIntents.Sale) }
    }
}