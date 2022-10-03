package com.xhivo97.inventorymanager.database

import android.net.Uri
import com.xhivo97.inventorymanager.model.Product

class InMemoryDatabase : Database {
    private val products = mutableListOf<Product>()

    // Product
    override fun addProduct(name: String, image: Uri?): Boolean {
        val product = products.find { it.name == name }
        if (product != null)
            throw DBProductAlreadyExists()

        return products.add(
            Product(
                name = name,
                image = image,
            )
        )
    }

    override fun getProductById(id: String): Product? {
        return products.find { it.id.toString() == id } ?: throw DBProductDoesNotExist()
    }

    override fun getProductByName(name: String): Product? {
        return products.find { it.name == name } ?: throw DBProductDoesNotExist()
    }

    override fun delProductById(id: String): Product? {
        val product = products.find { it.id.toString() == id } ?: throw DBProductDoesNotExist()

        return products.removeAt(products.indexOf(product))
    }

    override fun delProductByName(name: String): Product? {
        val product = products.find { it.name == name } ?: throw DBProductDoesNotExist()

        return products.removeAt(products.indexOf(product))
    }

    override fun getAllProducts(): List<Product> {
        return products
    }
}