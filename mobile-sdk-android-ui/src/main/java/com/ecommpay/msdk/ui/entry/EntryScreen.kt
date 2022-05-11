package com.ecommpay.msdk.ui.entry

import androidx.appcompat.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewIntents
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.entry.deleteDialog.DeleteDialog
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethod
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodIntents
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCard
import com.ecommpay.msdk.ui.entry.itemSaveCard.ItemSaveCardViewData
import com.ecommpay.msdk.ui.views.SDKToolBar

@Composable
fun EntryScreen(
    state: ViewStates<EntryViewData>?,
    intentListener: (intent: ViewIntents) -> Unit,
) {
    when (state) {
        is DefaultViewStates.Display,
        is EntryViewStates.ShownDeleteDialog -> {
            Scaffold(
                topBar = { SDKToolBar(null) },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        SaveCardList(
                            isEditableSavedCards = state.viewData.isEditableSavedCards,
                            saveCardList = state.viewData.saveCardList,
                            intentListener = intentListener)
                        PaymentMethodList(
                            paymentMethodList = state.viewData.paymentMethodList,
                            intentListener = intentListener)
                    }
                }
            )
        }
        is DefaultViewStates.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.onSurface))
        }
    }
    when (state) {
        is EntryViewStates.ShownDeleteDialog -> {
            DeleteDialog(state.viewData.deleteDialogViewData, intentListener)
        }
    }
}

@Composable
private fun SaveCardList(
    isEditableSavedCards: Boolean,
    saveCardList: List<ItemSaveCardViewData>,
    intentListener: (intent: ViewIntents) -> Unit,
) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.abc_star_black_48dp),
            contentDescription = "",
            modifier = Modifier
                .clickable { intentListener(EntryViewIntents.EditCard) }
                .align(CenterVertically))
        LazyRow(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()) {
            saveCardList.forEach {
                item {
                    ItemSaveCard(
                        viewData = it,
                        intentListener = intentListener,
                        isEditable = isEditableSavedCards,
                        iconCardUrl = it.iconCardUrl)
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }


}

@Composable
private fun PaymentMethodList(
    paymentMethodList: List<ItemPaymentMethodViewData>,
    intentListener: (intent: ItemPaymentMethodIntents) -> Unit,
) {
    paymentMethodList.forEach { itemPaymentMethodViewData ->
        ItemPaymentMethod(
            name = itemPaymentMethodViewData.name,
            intentListener = intentListener,
            iconUrl = itemPaymentMethodViewData.iconUrl)
        Spacer(
            modifier = Modifier.size(10.dp))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenDisplayPreview() {
    EntryScreen(DefaultViewStates.Display(EntryViewData.defaultViewData)) {}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenLoadingPreview() {
    EntryScreen(DefaultViewStates.Loading(EntryViewData.defaultViewData)) {}
}