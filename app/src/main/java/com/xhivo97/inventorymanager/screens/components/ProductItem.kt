package com.xhivo97.inventorymanager.screens.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xhivo97.inventorymanager.R
import com.xhivo97.inventorymanager.screens.util.ImageFromUri
import com.xhivo97.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun ProductItem(
    name: String,
    imageUri: Uri? = null,
    isDeleted: Boolean = false,
    deletePressed: (name: String) -> Unit = {},
) {
    val context = LocalContext.current
    var bitmap = remember { mutableStateOf(ImageFromUri().getBitmap(imageUri, context)) }

    Row(
        modifier = Modifier
            .background(color = when (isDeleted) {
                true -> MaterialTheme.colorScheme.tertiaryContainer
                false -> MaterialTheme.colorScheme.surface
            })
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!,
                contentDescription = stringResource(R.string.product_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.surfaceTint, CircleShape),
            )
        } else {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.surfaceTint, CircleShape)) {
                Icon(
                    imageVector = Icons.Rounded.Inventory2,
                    contentDescription = stringResource(R.string.product_preview),
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(modifier = Modifier.weight(1f), text = name)

        IconButton(onClick = { deletePressed(name) }) {
            Icon(
                imageVector = getDeleteIcon(isDeleted),
                contentDescription = stringResource(R.string.delete_product),
                tint = when (isDeleted) {
                    true -> MaterialTheme.colorScheme.tertiary
                    false -> MaterialTheme.colorScheme.secondary
                },
            )
        }
    }
}

@Preview(widthDp = 393, showBackground = true)
@Composable
fun ProductItemPreview() {
    InventoryManagerTheme {
        ProductItem(name = stringResource(R.string.product))
    }
}

fun getDeleteIcon(isDeleted: Boolean): ImageVector {
    return if (isDeleted) Icons.Filled.RestoreFromTrash else Icons.Filled.Delete
}