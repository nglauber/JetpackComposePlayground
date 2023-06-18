package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/*
 * https://nglauber.medium.com/lazy-layout-hexagonal-no-jetpack-compose-af0e98fab136
 */
@Composable
fun ListWithHexagonShapeScreen() {
    val list = remember {
        (0..50).map { "Item $it" }.toList()
    }
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val itemSize = (if (maxHeight < maxWidth) maxHeight else maxWidth) * .5f
        val overlap = itemOverlap(LocalDensity.current, itemSize) + 4.dp
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(overlap)
        ) {
            itemsIndexed(list) { index, item ->
                HexagonItem(
                    text = item,
                    index = index,
                    hexagonSize = itemSize
                )
            }
        }
    }
}

@Composable
fun HexagonItem(text: String, index: Int, hexagonSize: Dp) {
    val paddingValue = 8.dp + hexagonSize * .75f
    Box(
        modifier = Modifier
            .padding(
                start = if (index % 2 == 1) paddingValue else 0.dp,
                end = if (index % 2 == 0) paddingValue else 0.dp,
            )
            .size(hexagonSize)
            .clip(HexagonItemShape)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

val HexagonItemShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val minSize = min(size.width, size.height)
        val angleRadians = Math.toRadians(60.0).toFloat()
        val radius = minSize / 2f
        return Outline.Generic(
            Path().apply {
                (0..5).forEach { i ->
                    val currentAngle = angleRadians * i
                    val x = radius + radius * cos(currentAngle)
                    val y = radius + radius * sin(currentAngle)
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
        )
    }
}

private fun itemOverlap(density: Density, size: Dp): Dp {
    return with(density) {
        val sizeInPx = size.toPx()
        val radius = sizeInPx / 2f
        val y = radius + radius * sin(Math.toRadians(240.0))
        (-radius - y).toFloat().toDp()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHexagonItem() {
    JetpackComposePlaygroundTheme {
        ListWithHexagonShapeScreen()
    }
}