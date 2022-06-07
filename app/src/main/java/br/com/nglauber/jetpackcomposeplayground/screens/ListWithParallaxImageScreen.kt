package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
private fun ListBg(
    firstVisibleIndex: Int,
    totalVisibleItems: Int,
    firstVisibleItemOffset: Int,
    itemsCount: Int,
    maxWidth: Dp
) {
    val density = LocalDensity.current
    val firstItemOffsetDp =
        with(density) { firstVisibleItemOffset.toDp() } / itemsCount
    val hasNoScroll = itemsCount <= totalVisibleItems
    val totalWidth = if (hasNoScroll) maxWidth else maxWidth * 2
    val scrollableBgWidth = if (hasNoScroll) maxWidth else totalWidth - maxWidth
    val scrollStep = scrollableBgWidth / itemsCount
    val xOffset = if (hasNoScroll) 0.dp else -(scrollStep * firstVisibleIndex) - firstItemOffsetDp
    Box(
        Modifier
            .wrapContentWidth(unbounded = true, align = Alignment.Start)
            .offset(x = xOffset)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://placekitten.com/2000/400",
                contentScale = ContentScale.FillWidth,
            ),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .height(232.dp)
                .width(totalWidth)
        )
    }
}

@Composable
fun ListWithParallaxImageScreen() {
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
    val firstVisibleItemOffset by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset
        }
    }
    val itemsCount = 10
    BoxWithConstraints(Modifier.fillMaxSize()) {
        ListBg(firstVisibleIndex, totalVisibleItems, firstVisibleItemOffset, itemsCount, maxWidth)
        LazyRow(state = lazyListState, modifier = Modifier.fillMaxSize()) {
            items(itemsCount) {
                Card(
                    backgroundColor = Color.LightGray.copy(alpha = .5f),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(300.dp)
                        .height(200.dp)
                ) {
                    Text(
                        text = "Item $it",
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}