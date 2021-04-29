package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun ViewPagerScreen() {
    val images = listOf(
        R.drawable.recife,
        R.drawable.dog,
        R.drawable.male,
        R.drawable.female,
    )
    HorizontalPager(
        state = rememberPagerState(pageCount = images.size),
        offscreenLimit = 2
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