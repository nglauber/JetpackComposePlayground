package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmphasisScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text("No alpha applied - 100% opacity")
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text("High content alpha applied - 87% opacity")
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text("Medium content alpha applied - 60% opacity")
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text("Disabled content alpha applied - 38% opacity")
        }
        IntrinsicsSample("A\nB", "A")
        IntrinsicsSample("A", "A\nB")
        IntrinsicsSample("A", "B")
    }
}

@Composable
fun IntrinsicsSample(t1: String, t2: String) {
    Card(
        Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(Modifier.height(IntrinsicSize.Max)) {
            Box(
                Modifier
                    .background(Color.Yellow)
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(t1)
            }
            Box(
                Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    .background(Color.Green)
                    .padding(4.dp)
            ) {
                Text(t2)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmphasisScreen() {
    MaterialTheme {
        EmphasisScreen()
    }
}