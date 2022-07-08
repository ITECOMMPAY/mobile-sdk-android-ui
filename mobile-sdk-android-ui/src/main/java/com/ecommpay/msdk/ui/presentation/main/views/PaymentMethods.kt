package com.ecommpay.msdk.ui.presentation.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.method.NewCardItem
import com.ecommpay.msdk.ui.presentation.main.views.method.PaymentMethodItem
import com.ecommpay.msdk.ui.presentation.main.views.method.SavedCardItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.core.mergeUIPaymentMethods

@Composable
internal fun PaymentMethodList(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null //callback for show vat info
) {

    val mergedPaymentMethods =
        paymentMethods.mergeUIPaymentMethods(LocalContext.current, savedAccounts)

    if (mergedPaymentMethods.isEmpty()) return
    LaunchedEffect(Unit) {
        onItemSelected?.invoke(mergedPaymentMethods.first())
    }

    var selectedPaymentMethod by remember { mutableStateOf(mergedPaymentMethods.first()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        mergedPaymentMethods.forEach {
            when (it) {
                is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
                    SavedCardItem(
                        isExpand = selectedPaymentMethod.index == it.index,
                        paymentMethod = it
                    ) { method ->
                        selectedPaymentMethod = method
                        onItemSelected?.invoke(method)
                    }
                }
                is UIPaymentMethod.UICardPayPaymentMethod -> {
                    NewCardItem(
                        isExpand = selectedPaymentMethod.index == it.index,
                        paymentMethod = it,
                    ) { method ->
                        selectedPaymentMethod = method
                        onItemSelected?.invoke(method)
                    }
                }
                is UIPaymentMethod.UIGooglePayPaymentMethod -> {

                }
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        }
    }
}
