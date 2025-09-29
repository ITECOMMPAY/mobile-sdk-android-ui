package com.paymentpage.msdk.ui.views.card

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.RequestPermission
import com.paymentpage.msdk.ui.utils.RequestPermissionState.CameraRequestPermissionState
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.navigateToAppSettings
import com.paymentpage.msdk.ui.utils.extensions.stringResourceIdFromStringName
import com.paymentpage.msdk.ui.utils.rememberCameraRequestPermissionsState
import com.paymentpage.msdk.ui.views.common.alertDialog.MessageAlertDialog

@Composable
internal fun CardScanningItem(
    modifier: Modifier = Modifier,
    onScanningResult: (CardScanningActivityContract.Result) -> Unit,
) {
    val context = LocalContext.current
    val paymentOptions = LocalPaymentOptions.current
    val languageCode = paymentOptions.paymentInfo.languageCode
    //states
    var permissionRequestState by rememberCameraRequestPermissionsState(initRequest = false)
    val showState = CameraRequestPermissionState(initRequest = true)
    val hideState = CameraRequestPermissionState(initRequest = false)
    var showPermanentlyDeniedAlertDialog by remember { mutableStateOf(false) }
    //cardIO config
    val launcher =
        rememberLauncherForActivityResult(contract = CardScanningActivityContract()) { result ->
            onScanningResult(result)
        }
    val guideColor = SDKTheme.colors.primary
    //check permission
    RequestPermission(
        context = context,
        requestState = permissionRequestState,
        granted = {
            launcher.launch(CardScanningActivityContract.Config(guideColor.toArgb()))
        },
        showRational = {
            //we don't show rational alert dialog
        },
        permanentlyDenied = {
            showPermanentlyDeniedAlertDialog = true
        }
    )
    Box(
        modifier = modifier
            .clip(SDKTheme.shapes.radius6)
            .background(SDKTheme.colors.inputField)
            .border(
                width = 1.dp,
                color = SDKTheme.colors.highlight,
                shape = SDKTheme.shapes.radius6
            )
            .clickable {
                permissionRequestState = showState
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = SDKTheme.images.cardScanningLogo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        if (showPermanentlyDeniedAlertDialog)
            MessageAlertDialog(
                message = stringResource(
                    id = context.stringResourceIdFromStringName(
                        name = "title_camera_permission_label",
                        locale = languageCode
                    )
                ),
                onConfirmButtonClick = {
                    permissionRequestState = hideState
                    showPermanentlyDeniedAlertDialog = false
                    context.navigateToAppSettings()
                },
                onDismissButtonClick = {
                    permissionRequestState = hideState
                    showPermanentlyDeniedAlertDialog = false
                },
                dismissButtonText = getStringOverride(OverridesKeys.BUTTON_CANCEL),
                confirmButtonText = stringResource(
                    id = context.stringResourceIdFromStringName(
                        name = "button_camera_permission_label",
                        locale = languageCode
                    )
                ),
                brandColor = paymentOptions.primaryBrandColor
            )

    }

}