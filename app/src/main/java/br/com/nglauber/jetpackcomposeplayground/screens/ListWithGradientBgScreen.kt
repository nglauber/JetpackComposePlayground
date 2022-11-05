package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

// https://stackoverflow.com/questions/72140863/itemdecoration-in-jetpack-compose/72143882#72143882
@Composable
private fun ListBg(
    maxHeight: Dp,
    yOffset: () -> Dp,
) {
    Box(
        Modifier
            .wrapContentHeight(unbounded = true, align = Alignment.Top)
            .background(Color.Yellow)
            .offset { IntOffset(x = 0, y = yOffset().roundToPx()) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight)
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
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val itemsCount = 50
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
    val hasNoScroll by remember {
        derivedStateOf {
            itemsCount <= totalVisibleItems
        }
    }
    val totalHeight = remember(hasNoScroll) {
        if (hasNoScroll) screenHeight else screenHeight * 3
    }
    val yOffset by remember {
        derivedStateOf {
            val scrollableBgHeight = if (hasNoScroll) screenHeight else totalHeight - screenHeight
            val scrollStep = scrollableBgHeight / (itemsCount + 2 - totalVisibleItems)
            if (hasNoScroll) 0.dp else -(scrollStep * firstVisibleIndex)
        }
    }
    Box(Modifier.fillMaxSize()) {
        ListBg(totalHeight, yOffset = { yOffset })
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