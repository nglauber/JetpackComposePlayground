package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.regex.Pattern

private fun isValidEmail(emailStr: CharSequence) =
    Pattern
        .compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
        ).matcher(emailStr).find()

@Composable
fun Tab1MainScreen(paddingValues: PaddingValues, onDetailsSelected: () -> Unit) {
    var emailText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color(0xFF9575CD))
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(text = "Tab 1")
        TextField(
            value = emailText,
            onValueChange = { text ->
                emailText = text
                showError = !isValidEmail(text)
            },
            isError = showError,
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
        )
        if (showError) {
            Text("Email is invalid")
        }
        Button(onClick = onDetailsSelected) {
            Text("Next")
        }
    }
}