package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalPagerApi
@Composable
fun MultiScrollScreen() {
    // Constants
    val tabRowHeight = 48.dp
    val toolbarHeight = 240.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    // Things to Remember
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        HorizontalPager(count = 2, state = pagerState) {
            val color = if (it == 0) Color.Red else Color.Blue
            LazyColumn(
                contentPadding = PaddingValues(top = toolbarHeight + tabRowHeight)
            ) {
                items(100) { index ->
                    Text(
                        "I'm item $index", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = color
                    )
                }
            }
        }
        Column {
            Image(
                painter = painterResource(id = R.drawable.recife),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(toolbarHeight)
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
                    .fillMaxWidth()
            )
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                Modifier
                    .height(tabRowHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = toolbarOffsetHeightPx.value.roundToInt()
                        )
                    }
            ) {
                repeat(2) {
                    Tab(
                        selected = pagerState.currentPage == it,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }) {
                        Text(text = "Tab $it")
                    }
                }
            }
        }
    }
}