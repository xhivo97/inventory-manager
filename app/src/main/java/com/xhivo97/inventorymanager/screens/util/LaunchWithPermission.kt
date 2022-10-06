package com.xhivo97.inventorymanager.screens.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LaunchWithPermission(
    permission: String,
    permissionContent: @Composable (showRationale: Boolean, requestPermission: () -> Unit) -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission)

    if (permissionState.status.isGranted) {
        content()
    } else if (permissionState.status.shouldShowRationale) {
        permissionContent(permissionState.status.shouldShowRationale) {
            permissionState.launchPermissionRequest()
        }
    } else {
        permissionContent(permissionState.status.shouldShowRationale) {
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null),
            ).let { intent ->
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}
