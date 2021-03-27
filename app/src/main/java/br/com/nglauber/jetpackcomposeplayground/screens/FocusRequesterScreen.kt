package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.text.input.ImeAction

@Composable
fun FocusRequestScreen() {
    val focusRequesters = List(3) { FocusRequester() }

    Column {
        TextFieldWithFocusRequesters(focusRequesters[0], focusRequesters[1])
        TextFieldWithFocusRequesters(focusRequesters[1], focusRequesters[2])
        TextFieldWithFocusRequesters(focusRequesters[2], focusRequesters[0])
    }
}

@Composable
private fun TextFieldWithFocusRequesters(
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester
) {
    var state by rememberSaveable {
        mutableStateOf("Focus Transition Test")
    }
    TextField(
        value = state,
        onValueChange = { text -> state = text },
        modifier = Modifier
            .focusOrder(focusRequester) {
                nextFocusRequester.requestFocus()
            }
        ,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}