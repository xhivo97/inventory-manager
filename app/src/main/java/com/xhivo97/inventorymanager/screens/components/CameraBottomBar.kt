package com.xhivo97.inventorymanager.screens.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.xhivo97.inventorymanager.screens.common.PreviewBottomBarAll
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme
import io.github.g0dkar.qrcode.QRCode
import java.io.ByteArrayOutputStream

@Composable
fun CameraBottomBar(
    modifier: Modifier = Modifier,
    qrCode: String,
) {
    val qrCodeBitmap = ByteArrayOutputStream().let { outputStream ->
        QRCode(qrCode).render().writeImage(outputStream)
        outputStream.toByteArray().let { byteArray ->
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        }
    }

    Surface(
        tonalElevation = 3.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Image(
                bitmap = qrCodeBitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 128.dp, height = 128.dp)
                    .alpha(1.0f)
                    .padding(16.dp),
            )

            Text(qrCode, Modifier.padding(bottom = 32.dp))

            Row(modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically) {
                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.width(100.dp),
                ) {
                    Text("Add")
                }

                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.width(100.dp),

                    ) {
                    Text("Remove")
                }

                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.width(100.dp),
                ) {
                    Text("Search")
                }
            }
        }
    }
}

@PreviewBottomBarAll
@Composable
fun CameraBottomBarPreview() {
    InventoryManagerTheme {
        CameraBottomBar(qrCode = "c4186a75-9182-4c3b-9116-f9906ff56569")
    }
}