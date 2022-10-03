package com.xhivo97.inventorymanager.screens.util

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class ImageFromUri {
    fun getBitmap(imageUri: Uri?, context: Context): ImageBitmap? {
        return imageUri?.let {
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                null
            }
        }?.asImageBitmap()
    }
}