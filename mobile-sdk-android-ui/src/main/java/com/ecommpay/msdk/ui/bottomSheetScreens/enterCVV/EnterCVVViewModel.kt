package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import android.os.Bundle
import com.ecommpay.msdk.core.domain.entities.clarification.ClarificationField
import com.ecommpay.msdk.core.domain.entities.init.InitSavedAccount
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.core.domain.entities.payment.PaymentStatus
import com.ecommpay.msdk.core.domain.interactors.card.sale.AcsPage
import com.ecommpay.msdk.core.domain.interactors.card.sale.card.CardSaleDelegate
import com.ecommpay.msdk.core.domain.interactors.card.sale.card.savedCard.SavedCardSaleRequest
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.main.PaymentActivity
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.initData
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.msdkSession
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.payment
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.paymentInfo
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

class EnterCVVViewModel : BaseViewModel<EnterCVVViewData>() {

    override fun entryPoint(arguments: Bundle?) {
        val id = arguments?.getString("id")?.toLong()
        val viewData = initData.savedAccounts.find { it.id == id }?.toViewData() ?: defaultViewData()
        updateState(DefaultViewStates.Display(viewData = viewData))
    }

    override fun obtainIntent(intent: ViewIntents, currentState: ViewStates<EnterCVVViewData>) {
        when (intent) {
            is EnterCVVViewIntents.MoveToPaymentMethodsList -> moveToEntryScreen()
            is EnterCVVViewIntents.PayClick -> getDataForResultScreen(intent)
        }
    }

    private fun moveToEntryScreen() {
        launchAction(NavigationViewActions.EnterCVVBottomSheetToPaymentMethodsListScreen("entryScreen"))
    }

    private fun getDataForResultScreen(intent: EnterCVVViewIntents.PayClick) {
        updateState(DefaultViewStates.Loading(viewData = viewState.value?.viewData?.copy(buttonPayText = "${initData.translations["button_pay_loading"]}") ?: defaultViewData()))
        msdkSession.getSavedCardSaleInteractor().execute(
            SavedCardSaleRequest(
                cvv = intent.cvv,
                accountId = intent.id
            ), object : CardSaleDelegate {
                override fun onClarificationFields(
                    clarificationFields: List<ClarificationField>,
                    payment: Payment,
                ) {

                }

                override fun onCompleteWithDecline(payment: Payment) {
                    PaymentActivity.payment = payment
                    launchAction(NavigationViewActions.EnterCVVBottomSheetToResultBottomSheet("resultScreen"))
                }

                override fun onCompleteWithFail(status: String?, payment: Payment) {
                }

                override fun onCompleteWithSuccess(payment: Payment) {
                    PaymentActivity.payment = payment
                    launchAction(NavigationViewActions.EnterCVVBottomSheetToResultBottomSheet("resultScreen"))
                }

                override fun onError(message: String) {

                    updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                        ?: defaultViewData()))
                    launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                        message ?: "",
                        true)))
                }

                override fun onPaymentCreated() {
                }

                override fun onStatusChanged(status: PaymentStatus, payment: Payment) {

                }

                override fun onThreeDSecure(
                    acsPage: AcsPage,
                    isCascading: Boolean,
                    payment: Payment,
                ) {

                }
            }
        )
    }

    override fun defaultViewData() = EnterCVVViewData.defaultViewData
}

private fun InitSavedAccount.toViewData() = EnterCVVViewData(
    cardUrlLogo = cardUrlLogo ?: "",
    cardNumber = number?.takeLast(8) ?: "",
    payClickIntent = EnterCVVViewIntents.PayClick(id = id ?: -1, cvv = ""),
    buttonPayText = "${initData.translations["button_pay"]} ${paymentInfo.paymentAmount} ${paymentInfo.paymentCurrency}"
)
