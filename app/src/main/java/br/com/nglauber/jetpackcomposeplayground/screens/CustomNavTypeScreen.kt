package br.com.nglauber.jetpackcomposeplayground.screens

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Device(val id: String, val name: String) : Parcelable

@Composable
fun CustomNavTypeScreen1(
    onDeviceSelected: (Device) -> Unit,
) {
    val device = remember {
        Device(
            Random.nextInt(0, 100).toString(), "test"
        )
    }
    Column(Modifier.fillMaxSize()) {
        Text(text = "Screen 1 - $device")
        Button(onClick = {
            onDeviceSelected(device)
        }) {
            Text(text = "Go to Next Screen")
        }
    }
}

@Composable
fun CustomNavTypeScreen2(device: Device?) {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Screen 2 - $device")
    }
}