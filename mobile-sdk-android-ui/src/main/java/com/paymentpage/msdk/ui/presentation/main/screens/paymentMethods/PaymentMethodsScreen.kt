package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.ExpandablePaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun PaymentMethodsScreen(
    actionType: SDKActionType,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {

    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current

    val lastState = mainViewModel.lastState
    val isTryAgain = lastState.isTryAgain ?: false
    val isSaleWithToken = LocalPaymentOptions.current.paymentInfo.token != null
    val uiPaymentMethods = paymentMethodsViewModel.state.collectAsState().value.paymentMethods ?: throw IllegalStateException("Not found paymentMethods in State")

    val filteredUIPaymentMethods = with(uiPaymentMethods) {
        if (isSaleWithToken)
            filterIsInstance<UIPaymentMethod.UISavedCardPayPaymentMethod>()
        else if (isTryAgain)
            filter { it.paymentMethod.code == mainViewModel.payment?.method }
        else this
    }

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = if (actionType == SDKActionType.Verify)
            getStringOverride(OverridesKeys.BUTTON_AUTHORIZE)
        else
            getStringOverride(OverridesKeys.TITLE_PAYMENT_METHODS),
        scrollableContent = {
            ExpandablePaymentOverview(
                actionType = actionType,
                expandable = true
            )
            Spacer(modifier = Modifier.size(16.dp))
            PaymentMethodList(
                actionType = actionType,
                uiPaymentMethods = filteredUIPaymentMethods
            )
            Spacer(modifier = Modifier.size(6.dp))
            SDKFooter()
            Spacer(modifier = Modifier.size(25.dp))
        },
        onClose = onCancel
    )
}
