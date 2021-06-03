package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlin.random.Random

@Composable
fun RevealSwipeScreen() {
    var fromStart by remember { mutableStateOf(true) }
    var fromEnd by remember { mutableStateOf(true) }
    val items = remember { (1..100).map { "Item $it" to getRandomColor() } }
    Column(Modifier.fillMaxSize()) {
        Row {
            Checkbox(checked = fromStart, onCheckedChange = { fromStart = it })
            Text("From Start")
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(checked = fromEnd, onCheckedChange = { fromEnd = it })
            Text("From End")
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            rememberLazyListState()
        ) {
            items(items) {
                val (text, color) = it
                key(text, color) { // fix an issue that the swipe doesn't work after scroll down
                    RevealItem(
                        text = text,
                        color = color,
                        directions = mutableSetOf<RevealDirection>().apply {
                            if (fromStart) add(RevealDirection.StartToEnd)
                            if (fromEnd) add(RevealDirection.EndToStart)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RevealItem(text: String, color: Color, directions: Set<RevealDirection>) {
    RevealSwipe(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        backgroundCardModifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        directions = directions,
        hiddenContentStart = {
            Icon(
                modifier = Modifier.padding(horizontal = 25.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.White
            )
        },
        hiddenContentEnd = {
            Icon(
                modifier = Modifier.padding(horizontal = 25.dp),
                imageVector = Icons.Outlined.Delete,
                contentDescription = null
            )
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .requiredHeight(80.dp),
            backgroundColor = color,
            shape = it,
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = text
            )
        }
    }
}

private fun getRandomColor(): Color {
    return Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
}