package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R


@ExperimentalFoundationApi
@Composable
fun GridScreen() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(100) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(
                        when (it % 3) {
                            0 -> R.drawable.icon_facebook
                            1 -> R.drawable.icon_twitter
                            else -> R.drawable.icon_insta
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp)
                )
                Text("Item $it")
            }
        }
    }
}