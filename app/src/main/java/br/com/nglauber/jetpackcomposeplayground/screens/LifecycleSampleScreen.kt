package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun LifecycleSampleScreen() {
    val eventsLog = remember {
        mutableStateListOf<String>()
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> eventsLog.add("onCreate")
                Lifecycle.Event.ON_START -> eventsLog.add("onStart")
                Lifecycle.Event.ON_RESUME -> eventsLog.add("onResume")
                Lifecycle.Event.ON_PAUSE -> eventsLog.add("onPause")
                Lifecycle.Event.ON_STOP -> eventsLog.add("onStop")
                Lifecycle.Event.ON_DESTROY -> eventsLog.add("onDestroy")
                Lifecycle.Event.ON_ANY -> eventsLog.add("onAny")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column {
        Text(text = "Example of lifecycle. Pause and resume the app to see the log.")
        LazyColumn {
            items(eventsLog) {
                Text(
                    it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}