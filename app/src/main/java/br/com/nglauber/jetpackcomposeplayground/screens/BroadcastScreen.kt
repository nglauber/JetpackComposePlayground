package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlin.math.max
import kotlin.random.Random

@Composable
fun BroadcastScreen() {
    val context = LocalContext.current
    var lastPressedKey by remember { mutableStateOf("") }

    DisposableEffect(context) {
        val broadcast =
            object: BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    lastPressedKey = intent?.getStringExtra("value") ?: ""
                }
            }

        context.registerReceiver(broadcast, IntentFilter("SOME_ACTION"))

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }

    Column {
        Text("Last key pressed: $lastPressedKey")
        Button(onClick = {
            context.sendBroadcast(Intent("SOME_ACTION").apply {
                putExtra("value", "Message ${Random(100).nextInt()}")
            })
        }) {
            Text(text = "Send Broadcast")
        }
    }
}