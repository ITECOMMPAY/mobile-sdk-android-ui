package com.paymentpage.msdk.ui.presentation

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.SDKCommonProvider
import com.paymentpage.msdk.ui.SDKPaymentOptions
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.RootNavigationView
import com.paymentpage.msdk.ui.theme.HexToJetpackColor
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.alertDialog.ErrorAlertDialog
import com.paymentpage.msdk.ui.views.common.alertDialog.MessageAlertDialog

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun MainContent(
    activity: PaymentActivity,
    paymentOptions: SDKPaymentOptions,
    msdkSession: MSDKCoreSession,
) {
    var showDismissDialog by remember { mutableStateOf(false) }
    var needCloseWhenError by remember { mutableStateOf(false) }
    var errorResultState by remember { mutableStateOf<ErrorResult?>(null) }
    var drawerState by remember { mutableStateOf(BottomDrawerState(initialValue = BottomDrawerValue.Closed)) }
    val navigator = remember { Navigator() }
    LaunchedEffect(Unit) {
        drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded)
    }
    SDKTheme(brandColor = HexToJetpackColor.getColor(paymentOptions.brandColor)) {
        BottomDrawer(
            modifier = Modifier.wrapContentSize(),
            drawerContent = {
                SDKCommonProvider(
                    paymentOptions = paymentOptions,
                    msdkSession = msdkSession,
                ) {
                    RootNavigationView(
                        actionType = paymentOptions.actionType,
                        navigator = navigator,
                        delegate = activity,
                        onCancel = { showDismissDialog = true },
                        onError = { errorResult, needClose ->
                            if (needClose) {
                                activity.onError(errorResult.code, errorResult.message)
                            } else {
                                errorResultState = errorResult
                            }
                            needCloseWhenError = needClose
                        }
                    )
                }
            },
            drawerState = drawerState,
            drawerBackgroundColor = Color.Transparent,
            gesturesEnabled = false,
            drawerElevation = 0.dp
        ) {
            when {
                showDismissDialog -> {
                    MessageAlertDialog(
                        message = { Text(text = stringResource(R.string.payment_dismiss_confirm_message)) },
                        onConfirmButtonClick = { activity.onCancel() },
                        confirmButtonText = stringResource(R.string.ok_label),
                        onDismissButtonClick = { showDismissDialog = false },
                        dismissButtonText = stringResource(R.string.cancel_label)
                    )
                }
                errorResultState != null -> {
                    ErrorAlertDialog(
                        title = { Text(text = stringResource(R.string.error_label)) },
                        message = { Text(text = errorResultState?.message ?: "") },
                        onConfirmButtonClick = {
                            if (needCloseWhenError)
                                activity.onError(
                                    errorResultState?.code ?: ErrorCode.UNKNOWN,
                                    errorResultState?.message
                                )
                            errorResultState = null
                        },
                        confirmButtonText = stringResource(R.string.ok_label),
                        onDismissRequest = {
                            if (needCloseWhenError)
                                activity.onError(
                                    errorResultState?.code ?: ErrorCode.UNKNOWN,
                                    errorResultState?.message
                                )
                            errorResultState = null
                        })
                }
            }
        }
    }
}