package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun ListBg(
    firstVisibleIndex: Int,
    totalVisibleItems: Int,
    itemsCount: Int,
    maxHeight: Dp
) {
    val hasNoScroll = itemsCount <= totalVisibleItems
    val totalHeight = if (hasNoScroll) maxHeight else maxHeight * 3
    val scrollableBgHeight = if (hasNoScroll) maxHeight else totalHeight - maxHeight
    val scrollStep = scrollableBgHeight / (itemsCount + 2 - totalVisibleItems)
    val yOffset = if (hasNoScroll) 0.dp else -(scrollStep * firstVisibleIndex)
    Box(
        Modifier
            .wrapContentHeight(unbounded = true, align = Alignment.Top)
            .background(Color.Yellow)
            .offset(y = yOffset)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(totalHeight)
                .drawBehind {
                    drawRoundRect(
                        Brush.linearGradient(
                            0f to Color.Red,
                            0.6f to Color.DarkGray,
                            1.0f to Color.Green,
                        ),
                    )
                }
        )
    }
}

@Composable
fun ListWithGradientBgScreen() {
    val lazyListState = rememberLazyListState()
    val firstVisibleIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }
    val totalVisibleItems by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.size
        }
    }
    val itemsCount = 50
    BoxWithConstraints(Modifier.fillMaxSize()) {
        ListBg(firstVisibleIndex, totalVisibleItems, itemsCount, maxHeight)
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text(
                        text = "Some header",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Some infinite list of things",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(itemsCount) {
                Text(
                    text = "Item $it",
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }
        }
    }
}