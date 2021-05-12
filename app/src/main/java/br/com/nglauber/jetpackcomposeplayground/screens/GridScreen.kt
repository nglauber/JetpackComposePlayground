package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R


@ExperimentalFoundationApi
@Composable
fun GridScreen() {
    val list = (0..100).toList()
    val visible = remember {
        mutableStateListOf(*list.map { true }.toTypedArray())
    }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(list) { index, it ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val itemSize = Modifier.size(96.dp)
                if (visible[index]) {
                    Image(
                        painter = painterResource(
                            when (it % 3) {
                                0 -> R.drawable.icon_facebook
                                1 -> R.drawable.icon_twitter
                                else -> R.drawable.icon_insta
                            }
                        ),
                        contentDescription = null,
                        modifier = itemSize
                    )
                } else {
                    Spacer(itemSize)
                }
                Checkbox(checked = visible[index], { visible[index] = it })
                Text("Item $it")
            }
        }
    }
}