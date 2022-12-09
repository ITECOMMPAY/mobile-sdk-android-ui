package com.paymentpage.ui.msdk.sample.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.domain.ui.base.viewUseCase
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewState
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.paymentpage.ui.msdk.sample.ui.base.ComposeViewState
import com.paymentpage.ui.msdk.sample.ui.main.views.HideSavedWalletsCheckbox
import com.paymentpage.ui.msdk.sample.ui.main.views.PaymentFields
import com.paymentpage.ui.msdk.sample.ui.main.views.ProjectSettings
import com.paymentpage.ui.msdk.sample.ui.main.views.VersionInfo
import com.paymentpage.ui.msdk.sample.ui.main.views.apiHost.ApiHostCheckbox
import com.paymentpage.ui.msdk.sample.ui.main.views.apiHost.ApiHostFields
import com.paymentpage.ui.msdk.sample.ui.main.views.button.*
import com.paymentpage.ui.msdk.sample.ui.main.views.customization.CustomizationCheckbox
import com.paymentpage.ui.msdk.sample.ui.main.views.customization.CustomizationFields
import com.paymentpage.ui.msdk.sample.ui.main.views.googlePay.GooglePayCheckbox
import com.paymentpage.ui.msdk.sample.ui.main.views.googlePay.GooglePayFields
import com.paymentpage.ui.msdk.sample.ui.main.views.mockMode.MockModeCheckbox
import com.paymentpage.ui.msdk.sample.ui.main.views.mockMode.SelectMockMode

@Composable
internal fun MainState(
    route: MainHostScreens.MainScreen,
    viewUseCase: MainViewUC = viewUseCase(route.toString(), {
        MainViewUC()
    })
) {
    ComposeViewState(
        viewUseCase = viewUseCase
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
        Spacer(Modifier.size(padding))
        ProjectSettings(
            paymentData = paymentData,
            intentListener = intentListener
        )
        Spacer(Modifier.size(padding))
        PaymentFields(
            paymentData = paymentData,
            intentListener = intentListener
        )
        Spacer(Modifier.size(padding))
        AdditionalFieldsButton {
            intentListener(MainViewIntents.AdditionalFields)
        }
        Spacer(Modifier.size(padding))
        RecurrentButton {
            intentListener(MainViewIntents.Recurrent)
        }
        Spacer(Modifier.size(padding))
        ThreeDSecureButton {
            intentListener(MainViewIntents.ThreeDSecure)
        }
        Spacer(Modifier.size(padding))
        HideSavedWalletsCheckbox(
            isChecked = paymentData.hideSavedWallets
        ) { onCheckedChange ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        hideSavedWallets = onCheckedChange
                    )
                )
            )
        }
        Spacer(Modifier.size(padding))
        CustomizationCheckbox(
            isChecked = viewState.isVisibleCustomizationFields
        ) {
            intentListener(MainViewIntents.ChangeCustomizationCheckbox)
        }
        if (viewState.isVisibleCustomizationFields) {
            CustomizationFields(
                viewState = viewState,
                paymentData = paymentData,
                intentListener = intentListener
            )
        }
        Spacer(Modifier.size(padding))
        ApiHostCheckbox(
            isChecked = viewState.isVisibleApiHostFields
        ) {
            intentListener(MainViewIntents.ChangeApiHostCheckBox)
        }
        if (viewState.isVisibleApiHostFields) {
            ApiHostFields(
                paymentData = paymentData,
                intentListener = intentListener
            )
        }
        Spacer(Modifier.size(padding))
        GooglePayCheckbox(viewState.isVisibleGooglePayFields) {
            intentListener(MainViewIntents.ChangeGooglePayCheckBox)
        }
        if (viewState.isVisibleGooglePayFields) {
            GooglePayFields(
                paymentData = paymentData,
                intentListener = intentListener
            )
        }
        Spacer(Modifier.size(padding))
        MockModeCheckbox(viewState.isVisibleMockModeType) {
            intentListener(MainViewIntents.ChangeMockModeCheckbox)
        }
        if (viewState.isVisibleMockModeType) {
            Spacer(Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.LightGray)
                    .padding(horizontal = 10.dp),
                content = {
                    Spacer(modifier = Modifier.size(10.dp))
                    SelectMockMode(viewState, intentListener)
                    Spacer(modifier = Modifier.size(10.dp))
                }
            )
        }
        Spacer(Modifier.size(padding))
        SaleButton { intentListener(MainViewIntents.Sale) }
        Spacer(Modifier.size(padding))
        TokenizeButton { intentListener(MainViewIntents.Tokenize) }
    }
}