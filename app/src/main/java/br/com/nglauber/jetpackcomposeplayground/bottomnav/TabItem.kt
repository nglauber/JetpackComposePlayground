package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabItem(val route: String, val title: String, val icon: ImageVector) {
    object ListInfo : TabItem("tab1", "Items", Icons.Filled.List)
    object ProfileInfo : TabItem("tab2", "Profile", Icons.Filled.Settings)
}