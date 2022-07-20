package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.nglauber.jetpackcomposeplayground.R

sealed class TabItem(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Tab1 : TabItem("tab1", R.string.tab_1, Icons.Filled.List)
    object Tab2 : TabItem("tab2", R.string.tab_2, Icons.Filled.Settings)
}