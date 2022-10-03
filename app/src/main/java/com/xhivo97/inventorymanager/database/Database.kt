package com.xhivo97.inventorymanager.database

import android.net.Uri
import com.xhivo97.inventorymanager.model.Product

interface Database {
    //Product
    fun addProduct(
        name: String,
        image: Uri?,
    ): Boolean

    fun getProductById(id: String): Product?
    fun getProductByName(name: String): Product?

    fun delProductById(id: String): Product?
    fun delProductByName(name: String): Product?

    fun getAllProducts(): List<Product>
}