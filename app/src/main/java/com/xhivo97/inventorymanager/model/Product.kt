package com.xhivo97.inventorymanager.model

import android.net.Uri
import java.util.UUID

data class Product(
    val name: String,
    val image: Uri? = null,
    val id: UUID = UUID.randomUUID(),
)