package com.paymentpage.msdk.ui.presentation.main.screens.tokenize

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.TokenizeCardPayItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
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
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        title = getStringOverride(OverridesKeys.TITLE_PAYMENT_METHODS),
        scrollableContent = {
            TokenizeCardPayItem(method = lastSelectedMethod as? UIPaymentMethod.UITokenizeCardPayPaymentMethod ?: tokenizePaymentMethod)
        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = onCancel
    )
}