@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.presentation.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.reset
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod
import com.paymentpage.msdk.ui.presentation.main.views.method.PaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods

internal const val COUNT_OF_VISIBLE_CUSTOMER_FIELDS = 3

@Composable
internal fun PaymentMethodList(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    additionalFields: List<AdditionalField>,
) {
    val mainViewModel = LocalMainViewModel.current
    val lastSelectedMethod = mainViewModel.lastState.currentMethod

    val mergedPaymentMethods = paymentMethods.mergeUIPaymentMethods(savedAccounts = savedAccounts)
    if (mergedPaymentMethods.isEmpty()) return

    LaunchedEffect(Unit) {
        val lastOpenedMethod = mainViewModel.lastState.currentMethod
        val openedMethod = lastOpenedMethod
            ?: if (mergedPaymentMethods.first() is UiPaymentMethod.UIGooglePayPaymentMethod) //if first method is google pay
                mergedPaymentMethods[1.coerceAtMost(mergedPaymentMethods.size - 1)]
            else //first by default
                mergedPaymentMethods.first()
        mainViewModel.reset()
        mainViewModel.setCurrentMethod(openedMethod)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        mergedPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(method = if (lastSelectedMethod?.index == uiPaymentMethod.index) lastSelectedMethod else uiPaymentMethod)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        }
    }

}
