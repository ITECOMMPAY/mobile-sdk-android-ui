package com.paymentpage.msdk.ui.presentation

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
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
import com.paymentpage.msdk.ui.utils.extensions.stringResourceIdFromStringName
import com.paymentpage.msdk.ui.views.common.alertDialog.ErrorAlertDialog
import com.paymentpage.msdk.ui.views.common.alertDialog.MessageAlertDialog

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
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
    val context = LocalContext.current
    val languageCode = paymentOptions.paymentInfo.languageCode

    LaunchedEffect(Unit) {
        drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded)
    }
    SDKTheme(
        isDarkTheme = paymentOptions.isDarkTheme,
        brandColor = HexToJetpackColor.getColor(paymentOptions.brandColor)
    ) {
        BottomDrawer(
            modifier = Modifier
                .wrapContentSize()
                .semantics {
                    testTagsAsResourceId = true
                },
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
                        message = stringResource(
                            id = context.stringResourceIdFromStringName(
                                name = "payment_dismiss_confirm_message",
                                locale = languageCode
                            )
                        ),
                        onConfirmButtonClick = { activity.onCancel() },
                        confirmButtonText = stringResource(R.string.ok_label),
                        onDismissButtonClick = { showDismissDialog = false },
                        dismissButtonText = stringResource(
                            id = context.stringResourceIdFromStringName(
                                name = "cancel_label",
                                locale = languageCode
                            )
                        ),
                        brandColor = paymentOptions.brandColor
                    )
                }

                errorResultState != null -> {
                    ErrorAlertDialog(
                        title = stringResource(
                            id = context.stringResourceIdFromStringName(
                                name = "error_label",
                                locale = languageCode
                            )
                        ),
                        message = errorResultState?.message ?: "",
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
                        },
                        brandColor = paymentOptions.brandColor
                    )
                }
            }
        }
    }
}