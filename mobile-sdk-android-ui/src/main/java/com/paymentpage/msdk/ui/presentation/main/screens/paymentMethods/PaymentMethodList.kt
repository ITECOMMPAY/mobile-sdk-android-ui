package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.PaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.PaymentMethodAction
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethodListItem

@Composable
internal fun PaymentMethodList(
    actionType: SDKActionType,
    uiPaymentMethods: List<UIPaymentMethodListItem>,
    onToggleMethodSelection: (UIPaymentMethod) -> Unit,
    onActionClicked: (PaymentMethodAction) -> Unit
) {
    if (uiPaymentMethods.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth()) {
        val isOnlyOneMethodOnScreen = uiPaymentMethods.size == 1
        uiPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(
                method = uiPaymentMethod.method,
                actionType = actionType,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
                isSelected = uiPaymentMethod.isSelected,
                onToggleSelection = { onToggleMethodSelection(uiPaymentMethod.method) },
                onActionClicked = onActionClicked,
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}
