package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun DerivedStateScreen() {
    var count by remember {
        mutableStateOf(0)
    }
    val derivedCount by remember(count) {
        derivedStateOf {
            if (count % 2 == 0) {
                count * 2
            } else {
                count
            }
        }
    }
    var sideEffectCount by remember {
        mutableStateOf(count)
    }
    Column {
        Text("Count: $count\nDerivedCount: $derivedCount\nSideEffect: $sideEffectCount")
        Button(onClick = {
            count++
        }) {
            Text("Inc")
        }
    }
    SideEffect {
        if (count % 3 == 0) {
            sideEffectCount = count
        }
    }
}