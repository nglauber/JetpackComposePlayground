package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun HorizontalScrollScreen() {
    val items = (1..10).map { "Item $it" }
    val colors = remember { (1..10).map { getRandomColor() } }
    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState()
            ) {
                itemsIndexed(items) { index, item ->
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .width(maxWidth)
                            .padding(16.dp),
                        backgroundColor = colors[index]
                    ) {
                        Text(item)
                    }
                }
            }
        }
    }
}

private fun getRandomColor(): Color {
    return Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
}