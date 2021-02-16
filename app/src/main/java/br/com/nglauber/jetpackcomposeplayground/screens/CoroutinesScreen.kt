package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CoroutinesScreen() {
    val scope = rememberCoroutineScope()
    val count = remember { mutableStateOf(0) }
    var welcomeMsg by remember { mutableStateOf("") }
    LaunchedEffect(welcomeMsg) {
        val s = withContext(Dispatchers.IO) {
            delay(5_000)
            "Hello Compose!"
        }
        welcomeMsg = s
    }
    Column {
        if (welcomeMsg.isBlank()) {
            CircularProgressIndicator()
        } else {
            Text(welcomeMsg)
        }
        Text("Current count: ${count.value}")
        Button(onClick = {
            scope.launch {
                for (i in 1..10) {
                    withContext(Dispatchers.IO) {
                        delay(1_000)
                    }
                    count.value = i
                }
            }
        }, content = { Text("Start!") })
    }
}