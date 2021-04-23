package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun ViewPagerScreen() {
    val pages = (1..5).map { it.toString() }
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)
    val state = rememberPagerState(pageCount = pages.size)
    HorizontalPager(state = state, offscreenLimit = 2) { page ->
        Text(
            text = "Page ${pages[page]}",
            modifier = Modifier
                .fillMaxSize()
                .background(colors[page]),
            textAlign = TextAlign.Center
        )
    }
}