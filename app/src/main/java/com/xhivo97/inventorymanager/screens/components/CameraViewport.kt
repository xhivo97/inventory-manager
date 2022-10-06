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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
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
import com.xhivo97.inventorymanager.R
import com.xhivo97.inventorymanager.screens.common.PreviewAll
import com.xhivo97.inventorymanager.screens.util.LaunchWithPermission
import com.xhivo97.inventorymanager.screens.util.QRCodeScanner
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun CameraViewport(
    onScanned: (String) -> Unit = {},
) {
    LaunchWithPermission(
        permission = android.Manifest.permission.CAMERA,
        permissionContent = { showRationale, requestPermission ->
            CameraPermissionContent(
                showRationale = showRationale,
                requestPermission = requestPermission,
            )
        },
    ) {
        CameraPreview(onSuccess = onScanned)
    }
}

@Composable
private fun CameraPermissionContent(
    showRationale: Boolean,
    requestPermission: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = when (showRationale) {
                true -> ""
                else -> ""
            },
        )
        FilledTonalButton(
            onClick = { requestPermission() },
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("")
        }
    }
}

@Composable
fun CameraPreview(
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onSuccess: (String) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
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

@PreviewAll
@Composable
fun CameraPermissionContentPreview() {
    InventoryManagerTheme {
        Surface {
            CameraPermissionContent(showRationale = false, requestPermission = {})
        }
    }
}
