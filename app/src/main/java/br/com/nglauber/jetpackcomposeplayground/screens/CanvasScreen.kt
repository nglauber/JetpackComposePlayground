package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun CanvasScreen() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
    ) {
        CanvasSample()
        MovingSquare()
    }
}

@Composable
fun CanvasSample() {
    Canvas(modifier = Modifier
        .size(300.dp)
        .background(Color.Gray)) {
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