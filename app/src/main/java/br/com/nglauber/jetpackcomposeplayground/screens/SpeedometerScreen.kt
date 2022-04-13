package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SpeedometerScreen() {
    var targetValue by remember {
        mutableStateOf(0f)
    }
    val progress = remember(targetValue) { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()
    Column(Modifier.padding(16.dp)) {
        Slider(value = targetValue, onValueChange = { targetValue = it })
        val intValue = targetValue * 55
        Text(text = "${intValue.toInt()}")
        Button(onClick = {
            scope.launch {
                progress.animateTo(
                    targetValue = intValue,
                    animationSpec = tween(
                        durationMillis = 3000,
                        easing = FastOutLinearInEasing,
                    )
                )
            }
        }) {
            Text(text = "Go")
        }
        Speedometer(progress.value.toInt())
    }
}

@Composable
fun Speedometer(
    progress: Int,
) {
    val mainColor = Color(255, 113, 113)
    val secondaryColor = mainColor.copy(alpha = .2f)
    val paint = Paint().apply {
        color = mainColor
    }
    val arcDegrees = 275
    val startArcAngle = 135f
    val startStepAngle = -45
    val numberOfMarkers = 51
    val degreesMarkerStep = arcDegrees / numberOfMarkers

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onDraw = {
            drawIntoCanvas { canvas ->
                val w = drawContext.size.width
                val h = drawContext.size.height
                val centerOffset = Offset(w / 2f, h / 2f)
                val quarterOffset = Offset(w / 4f, h / 4f)

                // Drawing Center Arc background
                val centerArcSize = Size(w / 2f, h / 2f)
                val centerArcStroke = Stroke(20f, 0f, StrokeCap.Round)
                drawArc(
                    secondaryColor,
                    startArcAngle,
                    arcDegrees.toFloat(),
                    false,
                    topLeft = quarterOffset,
                    size = centerArcSize,
                    style = centerArcStroke
                )
                // Drawing Center Arc progress
                drawArc(
                    mainColor,
                    startArcAngle,
                    (degreesMarkerStep * progress).toFloat(),
                    false,
                    topLeft = quarterOffset,
                    size = centerArcSize,
                    style = centerArcStroke
                )
                // Drawing the pointer circle
                drawCircle(mainColor, 50f, centerOffset)
                drawCircle(Color.White, 25f, centerOffset)
                drawCircle(Color.Black, 20f, centerOffset)

                // Drawing Line Markers
                for ((counter, degrees) in (startStepAngle..(startStepAngle + arcDegrees) step degreesMarkerStep).withIndex()) {
                    val lineEndX = 80f
                    paint.color = mainColor
                    val lineStartX = if (counter % 5 == 0) {
                        paint.strokeWidth = 3f
                        0f
                    } else {
                        paint.strokeWidth = 1f
                        lineEndX * .2f
                    }
                    canvas.save()
                    canvas.rotate(degrees.toFloat(), w / 2f, h / 2f)
                    canvas.drawLine(
                        Offset(lineStartX, h / 2f),
                        Offset(lineEndX, h / 2f),
                        paint
                    )
                    // Drawing Pointer
                    if (counter == progress) {
                        paint.color = Color.Black
                        canvas.drawPath(
                            Path().apply {
                                moveTo(w / 2, (h / 2) - 5)
                                lineTo(w / 2, (h / 2) + 5)
                                lineTo(w / 4f, h / 2)
                                lineTo(w / 2, (h / 2) - 5)
                                close()
                            },
                            paint
                        )
                    }
                    canvas.restore()
                }
            }
        }
    )
}