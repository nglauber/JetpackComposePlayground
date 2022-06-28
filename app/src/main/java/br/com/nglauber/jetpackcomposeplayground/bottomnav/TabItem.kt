package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabItem(val route: String, val title: String, val icon: ImageVector) {
    object Tab1 : TabItem("tab1", "Tab 1", Icons.Filled.List)
    object Tab2 : TabItem("tab2", "Tab 2", Icons.Filled.Settings)
}