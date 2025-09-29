package com.ecommpay.ui.msdk.sample.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ecommpay.ui.msdk.sample.domain.ui.base.MessageUI
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewActions
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewState
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewUC
import com.ecommpay.ui.msdk.sample.ui.base.ComposeViewState
import com.ecommpay.ui.msdk.sample.ui.components.SDKInfoDialog
import com.ecommpay.ui.msdk.sample.ui.components.SDKToast
import com.ecommpay.ui.msdk.sample.ui.navigation.NavigationState
import com.paymentpage.msdk.ui.utils.copyInClipBoard

@Composable
internal fun SampleActivity.SampleState(
    viewUseCase: SampleViewUC
) {
    ComposeViewState(
        viewUseCase = viewUseCase,
        actionListener = { viewAction ->
            when (viewAction) {
                is SampleViewActions.StartPaymentSDK -> startPaymentPage()
                is SampleViewActions.CopyInClipboard -> copyInClipBoard(text = viewAction.text, textToast = viewAction.textToast)
            }
        }
    ) { viewState, intentListener ->
        SampleScreen(viewState, intentListener)
    }
}

@Composable
fun SampleScreen(
    viewState: SampleViewState,
    intentListener: (SampleViewIntents) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        NavigationState(
            startRoute = MainHostScreens.MainScreen
        )
    }

    with(viewState.uiMessage) {
        when (this) {
            is MessageUI.Toast -> SDKToast(message)
            is MessageUI.Dialogs.CancelYes -> {}
            is MessageUI.Dialogs.Info.SuccessTokenize -> {
                SDKInfoDialog(
                    iconID = iconID,
                    title = title,
                    message = message,
                    buttonText = buttonText
                ) {
                    intentListener(SampleViewIntents.ShowMessage(MessageUI.Empty))
                    intentListener(SampleViewIntents.CopyInClipboard(text = message, textToast = "Token was copied"))
                }
            }
            is MessageUI.Dialogs.Info -> {
                SDKInfoDialog(
                    iconID = iconID,
                    title = title,
                    message = message,
                    buttonText = buttonText
                ) {
                    intentListener(SampleViewIntents.ShowMessage(MessageUI.Empty))
                }
            }
            is MessageUI.Empty -> Unit
        }
    }

}
