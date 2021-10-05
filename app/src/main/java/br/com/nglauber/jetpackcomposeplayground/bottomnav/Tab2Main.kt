package br.com.nglauber.jetpackcomposeplayground.bottomnav

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

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
fun Tab2MainScreen(device: Device, onDetailsSelected: () -> Unit) {
    val onBackPressed = localBackToFirstTab.current
    Column(
        modifier = Modifier
            .background(Color(0xFF90A4AE))
            .fillMaxSize()
    ) {
        Text(text = "Tab 2 - $device")
        Button(onClick = onDetailsSelected) {
            Text("Next")
        }
    }
    BackHandler(onBack = {
        onBackPressed()
    })
}