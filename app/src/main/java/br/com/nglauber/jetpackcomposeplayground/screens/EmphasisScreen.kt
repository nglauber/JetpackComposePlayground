package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*

@Composable
fun EmphasisScreen() {
    Column {
        Text("No alpha applied - 100% opacity")
        Providers(LocalContentAlpha provides ContentAlpha.high) {
            Text("High content alpha applied - 87% opacity")
        }
        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            Text("Medium content alpha applied - 60% opacity")
        }
        Providers(LocalContentAlpha provides ContentAlpha.disabled) {
            Text("Disabled content alpha applied - 38% opacity")
        }
    }
}