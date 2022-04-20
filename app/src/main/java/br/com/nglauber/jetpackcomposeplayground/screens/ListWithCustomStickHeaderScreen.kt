package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListWithCustomStickHeaderScreen() {
    Box(Modifier.fillMaxSize()) {
        val groupedNames = remember(names) {
            names.groupBy { it.first() }
        }
        val startIndexes = remember(names) {
            getStartIndexes(groupedNames.entries)
        }
        val endIndexes = remember(names) {
            getEndIndexes(groupedNames.entries)
        }
        val commonModifier = Modifier.width(50.dp)
        val listState = rememberLazyListState()
        val moveStickyHeader = endIndexes.contains(listState.firstVisibleItemIndex + 1)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            itemsIndexed(names) { index, name ->
                NameItem(
                    name,
                    showCharHeader = startIndexes.contains(index) && listState.firstVisibleItemIndex != index,
                    commonModifier
                )
            }
        }
        LetterHeader(
            char = names[listState.firstVisibleItemIndex].first().toString(),
            modifier = commonModifier.then(
                if (moveStickyHeader) {
                    Modifier.offset {
                        IntOffset(0, -listState.firstVisibleItemScrollOffset)
                    }
                } else {
                    Modifier
                }
            )
        )
    }
}

@Composable
fun NameItem(
    name: String,
    showCharHeader: Boolean,
    modifier: Modifier
) {
    Row(Modifier.fillMaxWidth()) {
        if (showCharHeader) {
            LetterHeader(
                char = name.first().toString(),
                modifier = modifier
            )
        } else {
            Spacer(modifier = modifier)
        }
        Text(
            text = name,
            color = Color.DarkGray,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun LetterHeader(char: String, modifier: Modifier = Modifier) {
    Text(
        text = char,
        color = Color(117, 137, 199),
        fontSize = 28.sp,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

private fun getStartIndexes(entries: Set<Map.Entry<Char, List<String>>>): List<Int> {
    var acc = 0
    val list = mutableListOf<Int>()
    entries.forEach { entry ->
        list.add(acc)
        acc += entry.value.size
    }
    return list
}

private fun getEndIndexes(entries: Set<Map.Entry<Char, List<String>>>): List<Int> {
    var acc = 0
    val list = mutableListOf<Int>()
    entries.forEach { entry ->
        acc += entry.value.size
        list.add(acc)
    }
    return list
}