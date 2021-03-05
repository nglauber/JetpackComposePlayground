package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R

@Composable
fun CanvasScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        CanvasSample()
        MovingSquare()
    }
}

@Composable
fun CanvasSample() {
    val vectorPainter = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.ic_android_orange)
    )
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Gray)
    ) {
        drawCircle(
            color = Color.Red,
            radius = 300f
        )
        drawCircle(
            color = Color.Green,
            radius = 200f
        )
        drawCircle(
            color = Color.Blue,
            radius = 100f
        )
        with(vectorPainter) {
            inset(
                horizontal = 16.dp.toPx(),
                vertical = 16.dp.toPx()
            ) {
                draw(Size(96.dp.toPx(), 96.dp.toPx()))
            }
        }
    }
}


@Composable
fun MovingSquare() {
    val animPosX = remember { Animatable(initialValue = 0f) }
    LaunchedEffect("Square") {
        animPosX.animateTo(
            targetValue = 500f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(left = animPosX.value)
        }) {
            drawRect(color = Color.Red)
        }
    })
}