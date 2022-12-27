package com.paymentpage.msdk.ui.presentation.main.screens.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.decline.ResultDeclineSaleContent
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.decline.ResultDeclineTokenizeContent

@Composable
internal fun ResultDeclineScreen(
    actionType: SDKActionType? = null,
    onClose: (Payment) -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.payment ?: throw IllegalStateException("Not found payment in State")

    BackHandler(true) { onClose(payment) }

    when (actionType) {
        SDKActionType.Sale -> ResultDeclineSaleContent(onClose = onClose, onError = onError)
        SDKActionType.Tokenize -> ResultDeclineTokenizeContent(onClose = onClose, onError = onError)
        else -> Unit
    }

}

