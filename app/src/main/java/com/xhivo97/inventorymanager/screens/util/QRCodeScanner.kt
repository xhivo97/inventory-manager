package com.xhivo97.inventorymanager.screens.util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.xhivo97.inventorymanager.R
import java.util.UUID

class QRCodeScanner(
    private val onSuccess: (String) -> Unit,
    private val context: Context,
) : ImageAnalysis.Analyzer {

    private val scanner: BarcodeScanner = BarcodeScanning.getClient(BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build())

    private var oldCode = ""
    private var currCode = ""
    private var sameCodeCount = 0
    private var multipleToastShown = false

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image).addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) multipleToastShown = false

                if (barcodes.size == 1) {
                    barcodes[0].rawValue?.let { newCode ->
                        if (sameCodeCount == 3) {
                            try {
                                UUID.fromString(currCode)
                                onSuccess(currCode)
                            } catch (e: IllegalArgumentException) {
                                Toast.makeText(
                                    context,
                                    context.resources.getString(R.string.code_not_uuid),
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }

                        currCode = newCode
                        if (currCode != oldCode) {
                            sameCodeCount = 0
                        } else {
                            sameCodeCount++
                        }
                        oldCode = currCode
                    }

                    multipleToastShown = false
                }

                if (barcodes.size > 1 && !multipleToastShown) {
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.multiple_codes),
                        Toast.LENGTH_SHORT,
                    ).show()
                    multipleToastShown = true
                }
            }.addOnFailureListener {
                Log.d(TAG, "Could not read QR code: $it")
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}
