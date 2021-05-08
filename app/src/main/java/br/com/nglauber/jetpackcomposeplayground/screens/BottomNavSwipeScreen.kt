package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun BottomNavSwipeScreen() {
    val scope = rememberCoroutineScope()
    val images = listOf(
        R.drawable.recife,
        R.drawable.dog,
        R.drawable.male,
        R.drawable.female,
    )
    val pageState = rememberPagerState(pageCount = images.size)
    Scaffold(
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    for (page in images.indices) {
                        BottomNavigationItem(
                            icon = { Icon(Icons.Filled.Home, "Page $page") },
                            selected = page == pageState.currentPage,
                            onClick = {
                                scope.launch {
                                    pageState.animateScrollToPage(page)
                                }
                            },
                            selectedContentColor = Color.Magenta,
                            unselectedContentColor = Color.LightGray,
                            label = { Text(text = "Page $page") }
                        )
                    }
                }
            )
        },
    ) {
        HorizontalPager(
            state = pageState
        ) { page ->
            Image(
                painterResource(id = images[page]),
                null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}