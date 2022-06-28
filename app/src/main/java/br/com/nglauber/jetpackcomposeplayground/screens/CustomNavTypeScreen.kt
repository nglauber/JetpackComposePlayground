package br.com.nglauber.jetpackcomposeplayground.screens

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Device(val id: String, val name: String) : Parcelable

class AssetParamType : NavType<Device>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Device? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Device {
        return Gson().fromJson(value, Device::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Device) {
        bundle.putParcelable(key, value)
    }
}

@Composable
fun CustomNavTypeScreen1(
    onDeviceSelected: (String) -> Unit,
) {
    val device = remember {
        Device(
            Random.nextInt(0, 100).toString(), "test"
        )
    }
    Column(Modifier.fillMaxSize()) {
        Text(text = "Screen 1 - $device")
        Button(onClick = {
            val json = Uri.encode(Gson().toJson(device))
            onDeviceSelected(json)
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