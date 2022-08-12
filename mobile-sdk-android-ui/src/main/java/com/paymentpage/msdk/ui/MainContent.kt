package com.paymentpage.msdk.ui

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.RootNavigationView
import com.paymentpage.msdk.ui.theme.HexToJetpackColor
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun MainContent(
    activity: PaymentActivity,
    paymentOptions: SDKPaymentOptions,
    msdkSession: MSDKCoreSession,
    navigator: Navigator,
) {
    var showDismissDialog by remember { mutableStateOf(false) }
    var needCloseWhenError by remember { mutableStateOf(false) }
    var errorResultState by remember { mutableStateOf<ErrorResult?>(null) }
    var drawerState by remember { mutableStateOf(BottomDrawerState(initialValue = BottomDrawerValue.Closed)) }
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
            drawerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            when {
                showDismissDialog -> {
                    AlertDialog(
                        text = { Text(text = stringResource(R.string.payment_dismiss_confirm_message)) },
                        onDismissRequest = { showDismissDialog = false },
                        confirmButton = {
                            TextButton(onClick = { activity.onCancel() })
                            {
                                Text(
                                    text = stringResource(R.string.ok_label),
                                    color = SDKTheme.colors.brand
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDismissDialog = false })
                            {
                                Text(
                                    text = stringResource(R.string.cancel_label),
                                    color = SDKTheme.colors.brand
                                )
                            }
                        }
                    )
                }
                errorResultState != null -> {
                    AlertDialog(
                        title = { Text(text = stringResource(R.string.error_label)) },
                        text = { Text(text = errorResultState?.message ?: "") },
                        onDismissRequest = {
                            if (needCloseWhenError)
                                activity.onError(
                                    errorResultState?.code ?: ErrorCode.UNKNOWN,
                                    errorResultState?.message
                                )
                            errorResultState = null
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                if (needCloseWhenError)
                                    activity.onError(
                                        errorResultState?.code ?: ErrorCode.UNKNOWN,
                                        errorResultState?.message
                                    )
                                errorResultState = null
                            })
                            {
                                Text(
                                    text = stringResource(R.string.ok_label),
                                    color = SDKTheme.colors.errorTextColor
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}