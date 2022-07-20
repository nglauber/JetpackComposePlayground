package br.com.nglauber.jetpackcomposeplayground.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher
import br.com.nglauber.jetpackcomposeplayground.util.DrawableId
import br.com.nglauber.jetpackcomposeplayground.util.GraphicsLayerScale
import br.com.nglauber.jetpackcomposeplayground.util.GraphicsRotation

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)

fun graphicsLayerScale(scale: Float): SemanticsMatcher =
    SemanticsMatcher.expectValue(GraphicsLayerScale, scale)

fun graphicsLayerRotation(rotation: Float): SemanticsMatcher =
    SemanticsMatcher.expectValue(GraphicsRotation, rotation)