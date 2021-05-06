package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

class MyComposeView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        BoxScreen()
    }
}