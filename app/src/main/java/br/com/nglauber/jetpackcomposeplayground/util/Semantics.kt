package br.com.nglauber.jetpackcomposeplayground.util

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId

val GraphicsLayerScale = SemanticsPropertyKey<Float>("GraphicsLayerScale")
var SemanticsPropertyReceiver.graphicsLayerScale by GraphicsLayerScale

val GraphicsRotation = SemanticsPropertyKey<Float>("GraphicsRotation")
var SemanticsPropertyReceiver.graphicsRotation by GraphicsRotation