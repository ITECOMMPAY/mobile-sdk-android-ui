package com.mglwallet.ui.msdk.sample.domain.ui.sample

import com.mglwallet.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.mglwallet.ui.msdk.sample.domain.ui.base.MessageUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SampleViewUC: BaseViewUC<SampleViewIntents, SampleViewState>(SampleViewState()) {

    private var job = Job()
        get() {
            field.cancel()
            field = Job()
            return field
        }

    override suspend fun reduce(viewIntent: SampleViewIntents) {
        when(viewIntent) {
            is SampleViewIntents.ShowMessage -> {
                showMessage(viewIntent.uiMessage)
            }
            is SampleViewIntents.StartPaymentSDK -> {
                launchAction(SampleViewActions.StartPaymentSDK)
            }
            is SampleViewIntents.CopyInClipboard -> {
                launchAction(SampleViewActions.CopyInClipboard(viewIntent.text, viewIntent.textToast))
            }
        }
    }

    private suspend fun showMessage(uiMessage: MessageUI){
        useCaseScope.launch(job){
            when (uiMessage) {
                is MessageUI.Toast -> {
                    updateState(viewState.value.copy(uiMessage = uiMessage))
                    delay(uiMessage.time.time)
                    updateState(viewState.value.copy(uiMessage = MessageUI.Empty))
                }
                is MessageUI.Dialogs -> updateState(viewState.value.copy(uiMessage = uiMessage))
                is MessageUI.Empty -> updateState(viewState.value.copy(uiMessage = uiMessage))
            }
        }
    }
}
