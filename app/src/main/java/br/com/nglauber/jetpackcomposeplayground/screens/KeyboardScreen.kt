package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun KeyboardScreen() {
    var lastPressedKey by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color(0xFF9575CD))
            .fillMaxSize()
    ) {
        Text("Last key pressed: $lastPressedKey")
        KeyboardComponent { lastPressedKey = it }
    }
}

@Composable
fun KeyboardComponent(onPressed: (String) -> Unit) {
    val keysMatrix = arrayOf(
        arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        arrayOf("Z", "X", "C", "V", "B", "N", "M")
    )
    val refsMap = mutableMapOf<String, ConstrainedLayoutReference>()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(clip = false)
    ) {
        keysMatrix.forEachIndexed { _, keyRow ->
            keyRow.forEachIndexed { _, key ->
                val ref = createRef()
                refsMap[key] = ref
            }
        }
        keysMatrix.forEachIndexed { row, keyRow ->
            keyRow.forEachIndexed { column, key ->
                val ref = refsMap[key]!!
                val modifier = Modifier.constrainAs(ref) {
                    // Start
                    if (column == 0) {
                        start.linkTo(parent.start)
                    } else {
                        refsMap[keyRow[column - 1]]?.let {
                            start.linkTo(it.end)
                        }
                    }
                    // End
                    if (column == keyRow.lastIndex) {
                        end.linkTo(parent.end)
                    } else {
                        refsMap[keyRow[column + 1]]?.let {
                            end.linkTo(it.start)
                        }
                    }
                    // Top
                    if (row == 0) {
                        top.linkTo(parent.top)
                    } else {
                        refsMap[keysMatrix[row - 1][0]]?.let {
                            top.linkTo(it.bottom)
                        }
                    }
                }

                val modifierPressed = Modifier.constrainAs(createRef()) {
                    start.linkTo(ref.start)
                    end.linkTo(ref.end)
                    bottom.linkTo(ref.bottom)
                }
                KeyboardKey(
                    keyboardKey = key,
                    modifier = modifier,
                    modifierPressed = modifierPressed,
                    pressed = onPressed
                )
            }
        }
    }
}

@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier,
    modifierPressed: Modifier,
    pressed: (String) -> Unit
) {
    var isKeyPressed by remember { mutableStateOf(false) }
    Text(keyboardKey, Modifier
        .then(modifier)
        .pointerInput(Unit) {
            detectTapGestures(onPress = {
                isKeyPressed = true
                val success = tryAwaitRelease()
                if (success) {
                    isKeyPressed = false
                    pressed(keyboardKey)
                } else {
                    isKeyPressed = false
                }
            })
        }
        .background(Color.White)
        .padding(
            start = 12.dp,
            end = 12.dp,
            top = 16.dp,
            bottom = 16.dp
        )
    )
    if (isKeyPressed) {
        Text(
            keyboardKey, Modifier
                .then(modifierPressed)
                .background(Color.White)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 48.dp
                )
        )
    }
}