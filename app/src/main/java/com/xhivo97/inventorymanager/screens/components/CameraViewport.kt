package com.xhivo97.inventorymanager.screens.components

import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.xhivo97.inventorymanager.R
import com.xhivo97.inventorymanager.screens.util.QRCodeScanner

@Composable
fun CameraViewport(
    modifier: Modifier = Modifier,
    onScanned: (String) -> Unit = {},
) {
    RequestPermission {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CameraPreview(onSuccess = onScanned)
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onSuccess: (String) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).also { view ->
                view.scaleType = scaleType
                view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            }

            val previewUseCase = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(640, 480))
                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(ContextCompat.getMainExecutor(context),
                        QRCodeScanner(onSuccess, context))
                }

            try {
                ProcessCameraProvider.getInstance(context).get().apply {
                    unbindAll()
                    bindToLifecycle(lifecycleOwner, cameraSelector, previewUseCase, imageAnalysis)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            previewView
        },
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    when (cameraPermissionState.status) {
        PermissionStatus.Granted -> {
            content()
        }
        is PermissionStatus.Denied -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                val textToShow = when (cameraPermissionState.status.shouldShowRationale) {
                    true -> stringResource(R.string.camera_rationale)
                    false -> stringResource(R.string.camera_permission)
                }

                Text(
                    textToShow,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                FilledTonalButton(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text(stringResource(R.string.reuqest_permission))
                }
            }
        }
    }
}