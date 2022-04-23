package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Tab1DetailsScreen(paddingValues: PaddingValues) {
    Box(Modifier.padding(paddingValues)) {
        Text(text = "Tab 1 - Details")
    }
}