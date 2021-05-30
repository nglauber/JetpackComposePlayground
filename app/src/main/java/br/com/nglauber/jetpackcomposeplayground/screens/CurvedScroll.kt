package br.com.nglauber.jetpackcomposeplayground.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos

@Composable
fun CurvedScrollScreen() {
    val items = listOf(
        Icons.Default.Add to "Add",
        Icons.Default.Delete to "Delete",
        Icons.Default.Search to "Search",
        Icons.Default.Settings to "Settings",
        Icons.Default.Star to "Favorites",
        Icons.Default.Home to "Home"
    )
    Box(
        Modifier
            .background(Color.LightGray)
            .height(200.dp)
            .width(200.dp)
    ) {
        CurvedScroll(items.size) {
            val (icon, text) = items[it]
            Row(
                Modifier.clickable {
                    Log.d("NGVL", text)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = "", modifier = Modifier.padding(8.dp))
                Text(text)
            }
        }
    }
}

@Composable
fun CurvedScroll(
    itemCount: Int,
    item: @Composable (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    var size by remember { mutableStateOf(IntSize.Zero) }
    val scope = rememberCoroutineScope()
    val indices = remember { IntArray(itemCount) { 0 } }

    val flingBehavior = object : FlingBehavior {
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            val value = scrollState.value
            indices.minByOrNull { abs(it - value) }?.let {
                scope.launch {
                    scrollState.animateScrollTo(it)
                }
            }
            return initialVelocity
        }
    }

    Box(modifier = Modifier.onSizeChanged { size = it }) {
        Layout(
            modifier = Modifier.verticalScroll(scrollState, flingBehavior = flingBehavior),
            content = { repeat(itemCount) { item(it) } }
        ) { measurables, constraints ->
            val itemSpacing = 16.dp.roundToPx()
            var contentHeight = (itemCount - 1) * itemSpacing

            val placeables = measurables.mapIndexed { index, measurable ->
                val placeable = measurable.measure(constraints)
                contentHeight += if (index == 0 || index == measurables.lastIndex) {
                    placeable.height / 2
                } else {
                    placeable.height
                }
                placeable
            }

            layout(constraints.maxWidth, size.height + contentHeight) {
                val startOffset = size.height / 2 - placeables[0].height / 2
                var yPosition = startOffset

                val scrollPercent = scrollState.value.toFloat() / scrollState.maxValue

                placeables.forEachIndexed { index, placeable ->
                    val elementRatio = index.toFloat() / placeables.lastIndex
                    val interpolatedValue = cos((scrollPercent - elementRatio) * PI)
                    val indent = interpolatedValue * size.width / 2

                    placeable.placeRelativeWithLayer(x = indent.toInt(), y = yPosition) {
                        alpha = interpolatedValue.toFloat()
                    }
                    indices[index] = yPosition - startOffset
                    yPosition += placeable.height + itemSpacing
                }
            }
        }
    }
}