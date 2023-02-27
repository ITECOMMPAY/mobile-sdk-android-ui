package com.paymentpage.msdk.ui.utils

import android.Manifest
import android.content.Context
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.paymentpage.msdk.ui.utils.extensions.findActivity
import com.paymentpage.msdk.ui.utils.RequestPermissionState.CameraRequestPermissionState

/**
 * request permission or not
 */
internal sealed class RequestPermissionState(initRequest: Boolean, val permission: String) {
    var requestPermission by mutableStateOf(initRequest)

    class CameraRequestPermissionState(initRequest: Boolean) : RequestPermissionState(initRequest, Manifest.permission.CAMERA)
}

/**
 * Remember camera permission should be requested or not
 */
@Composable
internal fun rememberCameraRequestPermissionsState(
    initRequest: Boolean,
): MutableState<CameraRequestPermissionState> {
    return remember {
        mutableStateOf(CameraRequestPermissionState(initRequest))
    }
}

/**
 * Permission requester
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun RequestPermission(
    context: Context,
    requestState: RequestPermissionState,
    granted: () -> Unit,
    showRational: () -> Unit,
    permanentlyDenied: () -> Unit,
) {
    val permissionState =
        rememberPermissionState(permission = requestState.permission) { isGranted ->
            // This block will be triggered after the user chooses to grant or deny the permission
            // and we can tell if the user permanently declines or if we need to show rational
            val permissionPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                context.findActivity(), requestState.permission
            ) && !isGranted

            if (permissionPermanentlyDenied) {
                permanentlyDenied()
            } else if (!isGranted) {
                showRational()
            } else {
                granted()
            }
        }

    // If requestPermission, then launchPermissionRequest and the user will be able to choose
    // to grant or deny the permission.
    // After that, the RequestPermission will recompose and permissionState above will be triggered
    // and we can differentiate whether the permission is permanently declined or whether rational
    // should be shown
    if (requestState.requestPermission) {
        requestState.requestPermission = false
        if (permissionState.status.isGranted) {
            granted()
        } else {
            LaunchedEffect(key1 = Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}