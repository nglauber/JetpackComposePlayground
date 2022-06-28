package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Tab1DetailsScreen(paddingValues: PaddingValues, goToTab2details: () -> Unit) {
    Column(Modifier.padding(paddingValues)) {
        Text(text = "Tab 1 - Details")
        Button(onClick = goToTab2details) {
            Text("Go to Tab2 > Details")
        }
    }
}