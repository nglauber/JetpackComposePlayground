package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp

@Composable
fun AvatarStackScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()) {
        val backgroundGradient = remember {
            Brush.linearGradient(
                0.0f to Color.Red,
                1.0f to Color.Blue
            )
        }
        ChipStack(Modifier.background(backgroundGradient))
    }
}

@Composable
fun ChipStack(modifier: Modifier = Modifier) {
    val size = 80.dp
    val sizeModifier = Modifier.size(size)
    val colors = listOf(Color.Green, Color.Yellow, Color.Cyan, Color.Magenta, Color.LightGray)
    val width = (size / 2) * (colors.size + 1)
    Box(
        modifier = modifier
            .size(width, size)
            .graphicsLayer {
                alpha = 0.99f // slight alpha to force compositing layer
            },
    ) {
        var offset = 0.dp
        for (color in colors) {
            Chip(strokeWidth = 10.0f, sizeModifier.offset(offset)) {
                Image(ColorPainter(color), contentDescription = null)
            }
            offset += size / 2
        }
    }
}

@Composable
fun Chip(
    strokeWidth: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val stroke = remember(strokeWidth) {
        Stroke(width = strokeWidth)
    }
    Box(modifier = modifier
        .drawWithContent {
            drawContent()
            drawCircle(
                Color.Black,
                size.minDimension / 2,
                size.center,
                style = stroke,
                blendMode = BlendMode.Clear
            )
        }
        .graphicsLayer {
            clip = true
            shape = CircleShape
        }
    ) {
        content()
    }
}