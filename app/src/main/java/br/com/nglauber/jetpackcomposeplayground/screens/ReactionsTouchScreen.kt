package br.com.nglauber.jetpackcomposeplayground.screens

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@ExperimentalComposeUiApi
@Composable
fun ReactionsTouchScreen() {
    val icons = listOf(
        "Like" to Icons.Default.ThumbUp,
        "Loved" to Icons.Default.Favorite,
        "Star" to Icons.Default.Star,
        "Check" to Icons.Default.Check,
        "Home" to Icons.Default.Home,
    )
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ReactionsComponent(icons)
    }
}

@ExperimentalComposeUiApi
@Composable
fun ReactionsComponent(icons: List<Pair<String, ImageVector>>) {
    // Indicates if the icons are visible or not (used for animation)
    var visible by remember {
        mutableStateOf(false)
    }
    // Indicates the hovered item index
    var hoverIndex by remember {
        mutableStateOf(-1)
    }
    // Indicates the selected index
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    val density = LocalDensity.current
    val hoverScale = 1.5f
    val iconSize = 48.dp
    val rowContentPadding = 8.dp
    val iconSizePx = with(density) { iconSize.toPx() }
    val boxPaddingPx = with(density) { rowContentPadding.toPx() }
    val iconHoveredSize = iconSize.times(hoverScale)

    val boxHeight = iconHoveredSize + // Icon Size when hovered
            rowContentPadding.times(2) + // Row Padding
            32.dp // Aprox. Reaction Text Height + Padding
    val boxHeightPx = with(density) { boxHeight.toPx() }
    val boxWidthModifier =
        if (!visible)
            Modifier.width(iconSize.times(icons.size) + rowContentPadding.times(2))
        else
            Modifier.width(IntrinsicSize.Min)
    val animationDuration = 300
    val delayBetweenItems = 100
    Box(
        Modifier
            .height(boxHeight)
            .then(boxWidthModifier)
            .pointerInteropFilter {
                val selection = ((it.x - boxPaddingPx) / iconSizePx).toInt()
                if (selection >= icons.size || selection < 0 || it.x < boxPaddingPx || it.y > boxHeightPx) {
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
        // Box containing the row background
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(iconSize + rowContentPadding.times(2))
                .background(Color.LightGray, CircleShape)
        )
        // Row containing the items
        Row(
            Modifier
                .align(Alignment.BottomStart)
                .width(IntrinsicSize.Min)
                .padding(rowContentPadding),
            verticalAlignment = Alignment.Bottom
        ) {
            icons.forEachIndexed { index, item ->
                val size = if (hoverIndex == index) iconHoveredSize else iconSize
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = animationDuration,
                            delayMillis = index * delayBetweenItems,
                            easing = FastOutSlowInEasing
                        )
                    ) {
                        with(density) { iconSize.roundToPx() }
                    } + fadeIn(
                        animationSpec = tween(
                            durationMillis = animationDuration,
                            delayMillis = index * delayBetweenItems,
                            easing = FastOutSlowInEasing
                        ),
                    )
                ) {
                    val (text, icon) = item
                    ReactionItem(
                        text = text,
                        icon = icon,
                        size = animateDpAsState(size).value,
                        isHovered = hoverIndex == index,
                        isSelected = index == selectedIndex,
                    )
                }
            }
        }
        // Play the start animation after the first composition
        LaunchedEffect(Unit) {
            delay(500)
            visible = true
        }
    }
}

@Composable
fun ReactionItem(
    text: String,
    icon: ImageVector,
    size: Dp,
    isHovered: Boolean,
    isSelected: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isHovered) {
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color.Black,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp)
            )
        }
        Box(
            Modifier
                .size(size)
                .border(1.dp, Color.LightGray, CircleShape)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colors.primary else Color.DarkGray,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}