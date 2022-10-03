package com.xhivo97.inventorymanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xhivo97.inventorymanager.model.Product
import com.xhivo97.inventorymanager.screens.common.PreviewAll
import com.xhivo97.inventorymanager.screens.components.ProductItem
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun ProductsScreen(
    products: List<Product>,
    modifier: Modifier = Modifier,
    toBeDeleted: List<Product> = listOf(),
    deletePressed: (name: String) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(products) { i, product ->
            Column {
                if (i == 0) {
                    Divider()
                }

                ProductItem(
                    name = product.name,
                    imageUri = product.image,
                    isDeleted = toBeDeleted.contains(product),
                    deletePressed = deletePressed,
                )
                Divider()
            }
        }
    }
}

@PreviewAll
@Composable
fun ProductScreenPreview() {
    InventoryManagerTheme {
        Surface {
            ProductsScreen(products = List(15) { Product(name = "Product $it") })
        }
    }
}