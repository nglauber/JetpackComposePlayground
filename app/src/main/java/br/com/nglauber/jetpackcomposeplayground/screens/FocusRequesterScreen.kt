package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun FocusRequestScreen() {
    val focusRequesters = remember {
        List(9) { FocusRequester() }
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        (0..focusRequesters.lastIndex).forEach { index ->
            val nextIndex = if (index == focusRequesters.lastIndex) 0 else index + 1
            TextFieldWithFocusRequesters(index, focusRequesters[index], focusRequesters[nextIndex])
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TextFieldWithFocusRequesters(
    index: Int,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester
) {
    var state by rememberSaveable {
        mutableStateOf("Focus Transition Text $index")
    }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }
    TextField(
        value = state,
        onValueChange = { text ->
            state = text
            coroutineScope.launch {
                bringIntoViewRequester.bringIntoView()
            }
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    coroutineScope.launch {
                        delay(400)
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                focusRequester.freeFocus()
                nextFocusRequester.requestFocus()
            }
        )
    )
}