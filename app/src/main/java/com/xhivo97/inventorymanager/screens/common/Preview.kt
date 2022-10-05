package com.xhivo97.inventorymanager.screens.common

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(heightDp = 675, widthDp = 393, name = "1 - Light Theme Phone")
annotation class PreviewLightPhone

@Preview(
    heightDp = 675,
    widthDp = 393,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "2 - Dark Theme Phone",
)
annotation class PreviewDarkPhone

@Preview(heightDp = 675, widthDp = 900, name = "3 - Light Theme Tablet")
annotation class PreviewLightTablet

@Preview(
    heightDp = 675,
    widthDp = 900,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "4 - Dark Theme Tablet",
)
annotation class PreviewDarkTablet

@PreviewLightPhone
@PreviewDarkPhone
@PreviewLightTablet
@PreviewDarkTablet
annotation class PreviewAll

@Preview(widthDp = 393, showBackground = true, name = "1 - Light Theme Phone")
annotation class PreviewBottomBarLightPhone

@Preview(
    widthDp = 393,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "2 - Dark Theme Phone",
)
annotation class PreviewBottomBarDarkPhone

@Preview(widthDp = 900, showBackground = true, name = "3 - Light Theme Tablet")
annotation class PreviewBottomBarLightTablet

@Preview(
    widthDp = 900,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "4 - Dark Theme Tablet",
)
annotation class PreviewBottomBarDarkTablet

@PreviewBottomBarLightPhone
@PreviewBottomBarDarkPhone
@PreviewBottomBarLightTablet
@PreviewBottomBarDarkTablet
annotation class PreviewBottomBarAll