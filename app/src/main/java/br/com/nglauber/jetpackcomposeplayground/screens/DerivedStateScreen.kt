package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.nglauber.jetpackcomposeplayground.R

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
    Column(
        Modifier.fillMaxSize()
    ) {
        Text(stringResource(id = R.string.count_text, count))
        Text(stringResource(id = R.string.msg_derived_count, derivedCount))
        Text(stringResource(id = R.string.msg_side_effect_count, sideEffectCount))
        Button(onClick = {
            count++
        }) {
            Text(stringResource(id = R.string.btn_inc))
        }
    }
    SideEffect {
        if (count % 3 == 0) {
            sideEffectCount = count
        }
    }
}