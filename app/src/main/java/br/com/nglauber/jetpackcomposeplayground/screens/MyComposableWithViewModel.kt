package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import br.com.nglauber.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme

@Composable
fun MyComposableWithViewModel(viewModel: MyViewModel?) {
    val count = viewModel?.count?.observeAsState()
    JetpackComposePlaygroundTheme {
        Column {
            Text("Count: ${count?.value ?: -1}")
            Button(onClick = {
                viewModel?.inc()
            }) {
                Text("Inc")
            }
        }
    }
}