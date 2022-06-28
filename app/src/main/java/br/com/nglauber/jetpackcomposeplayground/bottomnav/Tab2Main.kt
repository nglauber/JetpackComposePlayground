package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Tab2MainScreen(
    paddingValues: PaddingValues,
    onDetailsSelected: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF90A4AE))
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(text = "Tab 2")
        Button(onClick = onDetailsSelected) {
            Text("Go to Tab 2 > Details")
        }
    }
    BackHandler(onBack = {
        onBackPressed()
    })
}