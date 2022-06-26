package br.com.nglauber.jetpackcomposeplayground.paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import br.com.nglauber.jetpackcomposeplayground.paging.model.Character
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MarvelCharactersScreen(
    viewModel: MarvelCharactersViewModel
) {
    val lazyPagingItems =
        viewModel.charactersPagedListFlow.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            lazyPagingItems.refresh()
        },
    ) {
        LazyColumn(state = rememberLazyListState()) {
            items(lazyPagingItems) { character ->
                character?.let {
                    CharacterItem(it)
                }
            }
            val state = lazyPagingItems.loadState
            when {
                state.refresh is LoadState.Loading ||
                        state.append is LoadState.Loading -> {
                    item {
                        LoadingIndicator()
                    }
                }
                state.append is LoadState.Error ||
                        state.refresh is LoadState.Error -> {
                    item {
                        ErrorRetryIndicator(
                            onRefresh = {
                                lazyPagingItems.refresh()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val thumbnail = character.thumbnail
        Image(
            painter = rememberAsyncImagePainter(
                "${thumbnail.pathSec}.${thumbnail.extension}"
            ),
            contentDescription = null,
            modifier = Modifier.size(144.dp, 144.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = character.name,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorRetryIndicator(
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onRefresh
        ) {
            Text(text = "Retry")
        }
    }
}