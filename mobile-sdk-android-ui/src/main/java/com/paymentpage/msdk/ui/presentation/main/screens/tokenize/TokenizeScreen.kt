package com.paymentpage.msdk.ui.presentation.main.screens.tokenize

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.TokenizeCardPayItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun TokenizeScreen(
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val lastSelectedMethod = paymentMethodsViewModel.state.collectAsState().value.currentMethod

    val uiPaymentMethods = paymentMethodsViewModel.state.collectAsState().value.paymentMethods
        ?: throw IllegalStateException("Not found paymentMethods in State")
    val tokenizePaymentMethod =
        uiPaymentMethods.first() as UIPaymentMethod.UITokenizeCardPayPaymentMethod

    LaunchedEffect(key1 = Unit) {
        val lastOpenedMethod =
            paymentMethodsViewModel.lastState.currentMethod ?: tokenizePaymentMethod
        paymentMethodsViewModel.setCurrentMethod(lastOpenedMethod)
    }

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
        scrollableContent = {
            TokenizeCardPayItem(
                method = (lastSelectedMethod as?
                        UIPaymentMethod.UITokenizeCardPayPaymentMethod)
                    ?: tokenizePaymentMethod,
                isOnlyOneMethodOnScreen = true
            )
            Spacer(modifier = Modifier.size(16.dp))
            SDKFooter()
        },
        onClose = onCancel
    )
}