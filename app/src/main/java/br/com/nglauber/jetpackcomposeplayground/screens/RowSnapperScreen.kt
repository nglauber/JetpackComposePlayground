package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch

@ExperimentalSnapperApi
@Composable
fun RowSnapperScreen() {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val snapper = rememberSnapperFlingBehavior(lazyListState)
    val items = (0..10).map { "Item $it" }
    var xOffset = 0
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(5, xOffset)
                }
            }
        ) {
            Text(text = "Snap to index 5")
        }
        LazyRow(
            state = lazyListState,
            flingBehavior = snapper,
        ) {
            itemsIndexed(items) { index, item ->
                Layout(content = {
                    Box(
                        Modifier
                            .size(200.dp)
                            .padding(8.dp)
                            .background(Color.Gray)
                    ) {
                        Text(text = item, Modifier.align(Alignment.Center))
                    }
                }, measurePolicy = { measurables, constraints ->
                    val placeable = measurables.first().measure(constraints)
                    val maxWidthInPx = maxWidth.roundToPx()
                    val itemWidth = placeable.width
                    xOffset = (maxWidthInPx - itemWidth) / 2
                    val startSpace = if (index == 0) xOffset else 0
                    val endSpace = if (index == items.lastIndex) xOffset else 0
                    val width = startSpace + placeable.width + endSpace
                    layout(width, placeable.height) {
                        val x = if (index == 0) startSpace else 0
                        placeable.place(x, 0)
                    }
                })
            }
        }
    }
}