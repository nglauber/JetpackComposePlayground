package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun AnimatingListScreen() {
    var list by remember {
        mutableStateOf(emptyList<String>())
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = list.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn {
                itemsIndexed(list) { index, item ->
                    ListItem(string = item,
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 4.dp)
                            .animateEnterExit(
                                enter = slideInVertically {
                                    it * (index + 1)
                                }
                            )
                            .fillMaxWidth()
                            .background(Color.LightGray, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(500)
        list = (0..50).map { "Item $it" }
    }
}

@Composable
fun ListItem(string: String, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        Text(text = string, style = MaterialTheme.typography.h5)
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun PreviewAnimatingListScreen() {
    MaterialTheme {
        AnimatingListScreen()
    }
}