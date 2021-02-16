package br.com.nglauber.jetpackcomposeplayground.bottomnav

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(val id: String, val name: String) : Parcelable

@Composable
fun Tab2MainScreen(navController: NavHostController) {
    val onBackPressed = localBackToFirstTab.current
    Column(
        modifier = Modifier
            .background(Color(0xFF90A4AE))
            .fillMaxSize()
    ) {
        Text(text = "Tab 2")
        Button(onClick = {
            navController.currentBackStackEntry
                ?.arguments?.putParcelable("bt_device", Device("1", "test"))
            navController.navigate("tab2_details")
        }) {
            Text("Next")
        }
    }
    BackButtonHandler(onBackPressed = {
        onBackPressed()
    })
}