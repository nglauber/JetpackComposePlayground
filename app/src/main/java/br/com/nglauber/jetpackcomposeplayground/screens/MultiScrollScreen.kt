package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

// Source: https://stackoverflow.com/a/72329873/1094333

@ExperimentalPagerApi
@Composable
fun MultiScrollScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return if (available.y > 0) Offset.Zero else Offset(
                    x = 0f,
                    y = -scrollState.dispatchRawDelta(-available.y)
                )
            }
        }
    }
    BoxWithConstraints {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            Image(
                painter = painterResource(id = R.drawable.recife),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
            )
            Column(Modifier.height(screenHeight)) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
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
                HorizontalPager(
                    state = pagerState,
                    count = 2,
                    modifier = Modifier
                        .fillMaxHeight()
                        .nestedScroll(nestedScrollConnection)
                ) {
                    val color = if (it == 0) Color.Red else Color.Blue
                    LazyColumn(
                        Modifier.fillMaxSize(),
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
            }
        }
    }
}