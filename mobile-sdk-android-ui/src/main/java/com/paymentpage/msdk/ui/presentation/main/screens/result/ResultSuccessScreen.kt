package com.paymentpage.msdk.ui.presentation.main.screens.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.success.ResultSuccessSaleContent
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.success.ResultSuccessTokenizeContent

@Composable
internal fun ResultSuccessScreen(
    actionType: SDKActionType,
    onClose: (Payment) -> Unit,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.payment ?: throw IllegalStateException("Not found payment in State")

    BackHandler(true) { onClose(payment) }

    when (actionType) {
        SDKActionType.Sale,
        SDKActionType.Auth -> ResultSuccessSaleContent(
            onClose = onClose,
            onCancel = onCancel,
            onError = onError
        )
        SDKActionType.Tokenize -> ResultSuccessTokenizeContent(
            onClose = onClose,
            onError = onError
        )
        else -> Unit
    }

}
