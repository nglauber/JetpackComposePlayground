package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object BulletPlaceables
object ContentPlaceables

@Composable
fun SubcomposableSampleScreen() {
    val lines =
        (1..20).map { "A long text that I expect that has more than one line of text because I want to test that." }
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubcomposableSample(lines)
    }
}

@Composable
fun Bullet(index: Int) {
    Text(
        "$index.",
        Modifier.padding(end = 4.dp),
        textAlign = TextAlign.End
    )
}

@Composable
fun LineContent(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BulletListEntry(
    bulletWidth: Dp,
    bullet: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Row {
        Box(Modifier.width(bulletWidth), contentAlignment = Alignment.TopEnd) {
            bullet()
        }
        Box(Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
fun SubcomposableSample(lines: List<String>) {
    val localDensity = LocalDensity.current
    SubcomposeLayout { constraints ->

        // Measuring the max bullet size
        val maxBulletWidth = subcompose(BulletPlaceables) {
            lines.forEachIndexed { index, _ -> Bullet(index) }
        }.map {
            it.measure(constraints).width
        }.maxByOrNull {
            it
        }?.let { maxWidth ->
            // +1 to offset rounding errors during the conversion
            with(localDensity) { ((maxWidth + 1) / density).dp }
        } ?: 0.dp

        // Measure the actual content
        val placeables = subcompose(ContentPlaceables) {
            // This is the actual content I was rendering before,
            // just now we know the maximum bullet size so we can use it
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                lines.forEachIndexed { index, lineContent ->
                    BulletListEntry(
                        bullet = { Bullet(index) },
                        bulletWidth = maxBulletWidth,
                        content = { LineContent(text = lines[index]) },
                    )
                    if (index != lines.lastIndex) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
        val contentPlaceable = placeables
            .first()
            .measure(constraints)

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}