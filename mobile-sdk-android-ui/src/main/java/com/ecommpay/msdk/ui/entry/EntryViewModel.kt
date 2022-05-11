package com.ecommpay.msdk.ui.entry

import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.ecommpay.msdk.core.domain.interactors.card.remove.CardRemoveRequest
import com.ecommpay.msdk.core.domain.interactors.init.InitDelegate
import com.ecommpay.msdk.core.domain.interactors.init.InitRequest
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.entry.deleteDialog.DeleteDialogIntents
import com.ecommpay.msdk.ui.entry.deleteDialog.DeleteDialogViewData
import com.ecommpay.msdk.ui.navigation.NavigationViewActions
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodIntents
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCardIntents
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCardViewData
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.msdkSession
import com.ecommpay.msdk.ui.main.PaymentActivity.Companion.paymentInfo

class EntryViewModel : BaseViewModel<EntryViewData>() {

    override fun obtainIntent(
        intent: ViewIntents,
        currentState: ViewStates<EntryViewData>,
    ) {
        when (intent) {
            //DeleteDialogIntents
            is DeleteDialogIntents.Cancel -> cancelDeleteDialog(intent.id)
            is DeleteDialogIntents.Delete -> removeCard(intent.id)
            //EntryIntents
            is EntryViewIntents.EditCard -> editCard()
            //ItemPaymentMethod
            is ItemPaymentMethodIntents.Click -> moveToPaymentMethodScreen(intent)
            //ItemSaveCard
            is ItemSaveCardIntents.Click -> {
                if (viewState.value?.viewData?.isEditableSavedCards == false)
                    moveToEnterCVVBottomSheet(intent)
            }
            is ItemSaveCardIntents.Delete -> showDeleteDialog(intent.id)
        }
    }

    private fun showDeleteDialog(id: Long) {
        viewState.value?.viewData?.apply {
            with(this) {
                val saveCard = saveCardList.find { it.deleteIntent.id == id }
                val savedCards = mutableListOf<ItemSaveCardViewData>()
                saveCardList.forEach {
                    savedCards.add(it.copy(isShowDeleteIcon = if (it.deleteIntent.id == id) !it.isShowDeleteIcon else it.isShowDeleteIcon))
                }
                updateState(EntryViewStates.ShownDeleteDialog(
                    viewState.value?.viewData?.copy(
                        deleteDialogViewData = deleteDialogViewData.copy(
                            number = saveCard?.cardNumber ?: "",
                            deleteIntent = DeleteDialogIntents.Delete(id),
                            cancelIntent = DeleteDialogIntents.Cancel(id)
                        ),
                        saveCardList = savedCards
                    ) ?: defaultViewData()))
            }
        }
    }

    private fun moveToPaymentMethodScreen(intent: ItemPaymentMethodIntents.Click) {
        launchAction(NavigationViewActions.PaymentMethodsListScreenToPaymentMethodScreen(navRoute = "paymentMethodScreen/${intent.name}"))
    }

    private fun moveToEnterCVVBottomSheet(intent: ItemSaveCardIntents.Click) {
        launchAction(NavigationViewActions.PaymentMethodsListScreenToEnterCVVBottomSheet(navRoute = "enterCVVScreen/${intent.id}"))
    }

    private fun editCard() {
        updateState(DefaultViewStates.Display(
            viewState.value?.viewData?.copy(isEditableSavedCards = !(viewState.value?.viewData?.isEditableSavedCards
                ?: false)) ?: defaultViewData()))
    }

    private fun cancelDeleteDialog(id: Long) {
        updateState(DefaultViewStates.Display(viewState.value?.viewData?.apply {
            saveCardList.find { it.deleteIntent.id == id }
                ?.apply { isShowDeleteIcon = !isShowDeleteIcon }
        } ?: defaultViewData()))
    }

    private fun removeCard(id: Long) {
        updateState(DefaultViewStates.Loading(viewState.value?.viewData ?: defaultViewData()))
        val removeInteractor = msdkSession.getCardRemoveInteractor()
        //Remove card
        removeInteractor.execute(CardRemoveRequest(id), object : CardRemoveDelegate {
            override fun onError(message: String) {
                updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                    ?: defaultViewData()))
                launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                    message ?: "",
                    true)))
            }

            override fun onSuccess(result: Boolean) {
                updateState(DefaultViewStates.Display(
                    viewState.value?.viewData?.copy(
                        saveCardList = viewState.value?.viewData?.saveCardList?.filter { it.deleteIntent.id != id }
                            ?: listOf()
                    ) ?: defaultViewData()
                ))
            }
        })
    }

    override fun entryPoint() {
        getData()
    }

    override fun defaultViewData() = EntryViewData.defaultViewData

    //Testing API
    private fun getData() {
        updateState(DefaultViewStates.Loading(viewState.value?.viewData ?: defaultViewData()))
        val initInteractor = msdkSession.getInitInteractor()
        initInteractor.execute(
            request = InitRequest(
                paymentInfo = paymentInfo,
                recurrentInfo = null,
                threeDSecureInfo = null
            ),
            callback = object : InitDelegate {
                //Init
                override fun onInitReceived() {
                    updateState(
                        DefaultViewStates.Display(
                            EntryViewData(
                                paymentMethodList = msdkSession.getPaymentMethods()?.map { it.toViewData() } ?: emptyList(),
                                saveCardList = msdkSession.getSavedAccounts()?.map { it.toViewData() } ?: emptyList(),
                                isEditableSavedCards = false,
                                deleteDialogViewData = DeleteDialogViewData(
                                    "Do you want to delete card?",
                                    "",
                                    DeleteDialogIntents.Delete(-1),
                                    DeleteDialogIntents.Cancel(-1),
                                )
                            )))
                }

                override fun onError(message: String) {
                    updateState(DefaultViewStates.Display(viewData = viewState.value?.viewData
                        ?: defaultViewData()))
                    launchAction(DefaultViewActions.ShowMessage(MessageAlert("Ошибка сети",
                        message ?: "",
                        true)))
                }

                //Restore payment
                override fun onPaymentRestored(payment: Payment) {

                }
            }
        )
    }


    fun PaymentMethod.toViewData() = ItemPaymentMethodViewData(
        name = name ?: "",
        iconUrl = iconUrl ?: ""
    )

    fun SavedAccount.toViewData() = ItemSaveCardViewData(
        cardNumber = number?.takeLast(8) ?: "",
        clickIntent = ItemSaveCardIntents.Click(id = id ?: -1),
        deleteIntent = ItemSaveCardIntents.Delete(id = id ?: -1),
        isShowDeleteIcon = true,
        iconCardUrl = cardUrlLogo
    )

//    private fun codeToIcon(code: String): Int {
//        return when (code) {
//            "card" -> {
//                R.drawable.btn_star_big_on
//            }
//            "qiwi" -> {
//                R.drawable.ic_menu_more
//            }
//            else -> {
//                R.drawable.ic_delete
//            }
//        }
//    }
}