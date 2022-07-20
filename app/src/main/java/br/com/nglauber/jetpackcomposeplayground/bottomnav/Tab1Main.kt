package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.nglauber.jetpackcomposeplayground.R
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
        Text(text = stringResource(id = R.string.tab_1))
        TextField(
            value = emailText,
            onValueChange = { text ->
                emailText = text
                showError = !isValidEmail(text)
            },
            isError = showError,
            label = { Text(text = stringResource(id = R.string.msg_email)) },
            modifier = Modifier.fillMaxWidth(),
        )
        if (showError) {
            Text(stringResource(id = R.string.msg_invalid_email))
        }
        Button(onClick = onDetailsSelected) {
            Text(stringResource(id = R.string.btn_next))
        }
    }
}