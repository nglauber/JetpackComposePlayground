package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import java.util.regex.Pattern

private fun isValidEmail(emailStr: String?) =
    Pattern
        .compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
        ).matcher(emailStr).find()

@Composable
fun Tab1MainScreen(navController: NavHostController) {
    var emailText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color(0xFF9575CD))
            .fillMaxSize()
    ) {
        Text(text = "Tab 1")
        TextField(
            value = emailText,
            onValueChange = {
                emailText = it
                showError = !isValidEmail(it)
            },
            isErrorValue = showError,
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text("Email is invalid")
        }
        Button(onClick = {
            navController.navigate("tab1_details")
        }) {
            Text("Next")
        }
    }
}