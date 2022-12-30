package com.paymentpage.msdk.ui.presentation.main.screens.tokenize

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.TokenizeCardPayItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun TokenizeScreen(
    tokenizePaymentMethod: UIPaymentMethod.UITokenizeCardPayPaymentMethod,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val mainViewModel = LocalMainViewModel.current
    val state = mainViewModel.state.collectAsState().value //for recomposition
    val lastSelectedMethod = state.currentMethod

    LaunchedEffect(key1 = Unit) {
        val lastOpenedMethod = mainViewModel.lastState.currentMethod ?: tokenizePaymentMethod
        mainViewModel.setCurrentMethod(lastOpenedMethod)
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