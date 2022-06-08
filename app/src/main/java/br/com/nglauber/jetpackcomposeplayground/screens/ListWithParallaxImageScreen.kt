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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

// https://stackoverflow.com/questions/72528090/offset-a-wide-image-for-horizontal-parallax-effect-in-android-compose/72533004#72533004
@Composable
private fun ListBg(
    firstVisibleIndex: Int,
    totalVisibleItems: Int,
    firstVisibleItemOffset: Int,
    itemsCount: Int,
    itemWidth: Dp,
    maxWidth: Dp
) {
    val density = LocalDensity.current
    val firstItemOffsetDp = with(density) { firstVisibleItemOffset.toDp() }
    val hasNoScroll = itemsCount <= totalVisibleItems
    val totalWidth = if (hasNoScroll) maxWidth else maxWidth * 2
    val scrollableBgWidth = if (hasNoScroll) maxWidth else totalWidth - maxWidth
    val scrollStep = scrollableBgWidth / itemsCount
    val firstVisibleScrollPercentage = firstItemOffsetDp.value / itemWidth.value
    val xOffset =
        if (hasNoScroll) 0.dp else -(scrollStep * firstVisibleIndex) - (scrollStep * firstVisibleScrollPercentage)
    Box(
        Modifier
            .wrapContentWidth(unbounded = true, align = Alignment.Start)
            .offset { IntOffset(x = xOffset.roundToPx(), y = 0) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://raw.githubusercontent.com/nglauber/JetpackComposePlayground/master/misc/kitten.jpeg",
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
    val itemWidth = 300.dp
    val itemPadding = 16.dp
    BoxWithConstraints(Modifier.fillMaxSize()) {
        ListBg(
            firstVisibleIndex,
            totalVisibleItems,
            firstVisibleItemOffset,
            itemsCount,
            itemWidth + (itemPadding * 2),
            maxWidth
        )
        LazyRow(state = lazyListState, modifier = Modifier.fillMaxSize()) {
            items(itemsCount) {
                Card(
                    backgroundColor = Color.LightGray.copy(alpha = .5f),
                    modifier = Modifier
                        .padding(itemPadding)
                        .width(itemWidth)
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