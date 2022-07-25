package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BackdropScaffoldScreen() {
    val clicksToReveal = 5
    var count by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val shouldShow = count >= clicksToReveal
    LaunchedEffect(state) {
        if (!shouldShow && state.isConcealed) {
            coroutineScope.launch {
                state.reveal()
            }
        }
    }
    val text = if (!shouldShow) "Click ${clicksToReveal - count} times to reveal"
    else if (state.isConcealed) "Hide"
    else "Show"
    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        appBar = {},
        backLayerContent = {
            Column(
                Modifier.fillMaxSize()
            ) {
                Text(
                    text,
                    modifier = Modifier.clickable {
                        count++
                        if (!shouldShow) return@clickable

                        if (state.isConcealed) {
                            coroutineScope.launch {
                                state.reveal()
                            }
                        } else {
                            coroutineScope.launch {
                                state.conceal()
                            }
                        }
                    })
            }
        },
        headerHeight = if (!shouldShow) 0.dp else BackdropScaffoldDefaults.HeaderHeight,
        frontLayerContent = {
            Text("FRONT")
        },
        scaffoldState = state,
        stickyFrontLayer = false
    )
}