package br.com.nglauber.jetpackcomposeplayground.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import br.com.nglauber.jetpackcomposeplayground.util.CoilAsyncPainter
import br.com.nglauber.jetpackcomposeplayground.util.DrawableId
import br.com.nglauber.jetpackcomposeplayground.util.GraphicsLayerScale
import br.com.nglauber.jetpackcomposeplayground.util.GraphicsRotation
import coil.compose.AsyncImagePainter

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)

fun graphicsLayerScale(scale: Float): SemanticsMatcher =
    SemanticsMatcher.expectValue(GraphicsLayerScale, scale)

fun graphicsLayerRotation(rotation: Float): SemanticsMatcher =
    SemanticsMatcher.expectValue(GraphicsRotation, rotation)

fun SemanticsNodeInteraction.isAsyncPainterComplete(): SemanticsNodeInteraction {
    assert(
        SemanticsMatcher("Async Image is Success") { semanticsNode ->
            val painter = semanticsNode.config.getOrElseNullable(CoilAsyncPainter) { null }
            painter?.state is AsyncImagePainter.State.Success
        }
    )
    return this
}