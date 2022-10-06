package com.xhivo97.inventorymanager.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import com.xhivo97.inventorymanager.screens.common.PreviewAll
import com.xhivo97.inventorymanager.screens.components.CameraBottomBar
import com.xhivo97.inventorymanager.screens.components.CameraViewport
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraScreen() {
    CameraScreen { onScanned ->
        CameraViewport(onScanned = onScanned)
    }
}

@Composable
private fun CameraScreen(
    code: String = "",
    cameraView: @Composable (onScanned: (String) -> Unit) -> Unit,
) {
    var qrCode by rememberSaveable { mutableStateOf(code) }

    @OptIn(ExperimentalMaterial3Api::class) Scaffold(
        bottomBar = {
            @OptIn(ExperimentalAnimationApi::class) AnimatedVisibility(
                visible = qrCode.isNotEmpty(),
                enter = EnterTransition.None.plus(slideInVertically(initialOffsetY = { -40 }))
                    .plus(expandVertically(expandFrom = Alignment.Bottom))
                    .plus(scaleIn(transformOrigin = TransformOrigin(0.5f, 0.5f)))
                    .plus(fadeIn(initialAlpha = 0.3f)),
                exit = ExitTransition.None.plus(slideOutVertically())
                    .plus(shrinkVertically())
                    .plus(fadeOut())
                    .plus(scaleOut(targetScale = 1.2f)),
            ) {
                CameraBottomBar(qrCode = qrCode)
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(24.dp),
            ) {
                cameraView { qrCode = it }
            }
        }
    }
}

@PreviewAll
@Composable
fun CameraScreenNoCodePreview() {
    InventoryManagerTheme {
        CameraScreen {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Camera Viewport")
            }
        }
    }
}

@PreviewAll
@Composable
fun CameraScreenWithCodePreview() {
    InventoryManagerTheme {
        CameraScreen(code = "c4186a75-9182-4c3b-9116-f9906ff56569") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Camera Viewport")
            }
        }
    }
}
