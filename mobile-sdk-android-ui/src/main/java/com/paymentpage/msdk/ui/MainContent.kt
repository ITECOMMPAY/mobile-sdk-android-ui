package com.paymentpage.msdk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.msdk.ui.navigation.NavigationComponent
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun MainContent(
    activity: PaymentActivity,
    paymentInfo: PaymentInfo,
    recurrentInfo: RecurrentInfo?,
    threeDSecureInfo: ThreeDSecureInfo?,
    recipientInfo: RecipientInfo?,
    additionalFields: List<AdditionalField>,
    msdkSession: MSDKCoreSession,
) {
    val showDialogDismissDialog = remember { mutableStateOf(false) }
    var drawerState by remember { mutableStateOf(BottomDrawerState(initialValue = BottomDrawerValue.Closed)) }
    LaunchedEffect(Unit) {
        drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded)
    }

    BottomDrawer(
        modifier = Modifier.wrapContentSize(),
        drawerContent = {
            SDKTheme() {
                SDKCommonProvider(
                    paymentInfo = paymentInfo,
                    recurrentInfo = recurrentInfo,
                    threeDSecureInfo = threeDSecureInfo,
                    recipientInfo = recipientInfo,
                    additionalFields = additionalFields,
                    msdkSession = msdkSession,
                ) {
                    NavigationComponent(
                        navigator = PaymentActivity.navigator,
                        delegate = activity,
                        onCancel = { showDialogDismissDialog.value = true }
                    )
                }
            }
        },
        drawerState = drawerState,
        drawerBackgroundColor = Color.Transparent,
        gesturesEnabled = false,
        drawerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f)),
        ) {
            if (showDialogDismissDialog.value)
                AlertDialog(
                    text = { Text(text = stringResource(R.string.payment_dismiss_confirm_message)) },
                    onDismissRequest = { showDialogDismissDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = { activity.onCancel() })
                        { Text(text = stringResource(R.string.ok_label)) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialogDismissDialog.value = false })
                        { Text(text = stringResource(R.string.cancel_label)) }
                    }
                )
        }
    }
}