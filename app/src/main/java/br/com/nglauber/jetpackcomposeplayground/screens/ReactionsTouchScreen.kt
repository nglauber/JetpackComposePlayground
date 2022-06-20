package br.com.nglauber.jetpackcomposeplayground.screens

import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun ReactionsTouchScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ReactionsComponent()
    }
}

@ExperimentalComposeUiApi
@Composable
fun ReactionsComponent() {
    val density = LocalDensity.current
    var hoverIndex by remember {
        mutableStateOf(-1)
    }
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    val iconSize = 48.dp
    val boxPadding = 8.dp
    val iconSizePx = with(density) { iconSize.toPx() }
    val boxPaddingPx = with(density) { boxPadding.toPx() }
    val increaseSize = iconSize.times(2f)
    val increaseSizePx = with(density) { increaseSize.toPx() }
    val icons = listOf(
        Icons.Default.Favorite,
        Icons.Default.Star,
        Icons.Default.Call,
        Icons.Default.AccountBox,
        Icons.Default.ThumbUp
    )
    Box(
        Modifier
            .height(increaseSize)
            .width(IntrinsicSize.Min)
            .pointerInteropFilter {
                val selection = ((it.x - boxPaddingPx) / iconSizePx).toInt()
                if (selection >= icons.size || selection < 0 || it.x < boxPaddingPx || it.y > increaseSizePx) {
                    hoverIndex = -1
                } else if (it.action == MotionEvent.ACTION_UP) {
                    selectedIndex = hoverIndex
                    hoverIndex = -1 // finger released
                } else {
                    hoverIndex = selection
                }
                true
            }
    ) {
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(iconSize + boxPadding.times(2))
                .background(Color.LightGray, CircleShape)
        )
        Row(
            Modifier
                .align(Alignment.BottomStart)
                .width(IntrinsicSize.Min)
                .padding(boxPadding),
            verticalAlignment = Alignment.Bottom
        ) {
            icons.forEachIndexed { index, icon ->
                val size = if (hoverIndex == index) increaseSize else iconSize
                Box(
                    Modifier
                        .border(1.dp, Color.LightGray, CircleShape)
                        .background(Color.White, CircleShape)
                        .height(animateDpAsState(size).value)
                        .width(animateDpAsState(size).value)
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (index == selectedIndex) Color.Blue else Color.Magenta,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}