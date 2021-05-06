package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CoroutinesScreen() {
    val scope = rememberCoroutineScope()
    val count = remember { mutableStateOf(0) }
    var welcomeMsg by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
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
            if (isLoading) return@Button
            scope.launch {
                isLoading = true
                for (i in 1..10) {
                    withContext(Dispatchers.IO) {
                        delay(1_000)
                    }
                    count.value = i
                }
                isLoading = false
            }
        }) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp, 22.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Start!")
            }
        }
    }
}