package com.xhivo97.inventorymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme
import java.util.concurrent.ExecutorService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryManagerTheme {
            }
        }
    }
}