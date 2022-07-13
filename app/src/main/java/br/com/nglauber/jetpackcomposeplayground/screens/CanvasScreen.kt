package br.com.nglauber.jetpackcomposeplayground.screens

import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.nglauber.jetpackcomposeplayground.R
import kotlinx.coroutines.isActive

@Composable
fun CanvasScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        CanvasSample()
        DrawGradientCircles()
        MovingSquare()
        Row() {
            ButtonWithCurvedText(R.drawable.ic_share, "Share", 48.dp, {})
            ButtonWithCurvedText(R.drawable.ic_cancel, "Cancel", 72.dp, {})
            ButtonWithCurvedText(R.drawable.ic_check, "Check", 96.dp, {})
        }
    }
}

//https://stackoverflow.com/questions/72944715/how-can-to-create-a-curved-text-around-a-canvas/72954381#72954381
@Composable
fun ButtonWithCurvedText(
    @DrawableRes icon: Int,
    text: String,
    iconSize: Dp,
    onClick: () -> Unit,
) {
    val density = LocalDensity.current
    val imgSize = iconSize
    val imageSizePx = with(density) { imgSize.toPx() }
    val labelSize = 12.sp
    val textToIconPadding = 4.dp
    val textToIconPaddingPx = with(density) { textToIconPadding.toPx() }
    val boxSize = imgSize + (textToIconPadding * 2) + with(density) { labelSize.roundToPx().toDp() }
    val boxSizePx = with(density) { boxSize.toPx() }

    val imageInset = ((boxSize - imgSize) / 2)
    val imageInsetPx = with(density) { imageInset.toPx() }

    val arcTop = imageInsetPx - (textToIconPaddingPx / 2f)
    val arcLeft = imageInsetPx - (textToIconPaddingPx / 2f)
    val arcBottom = boxSizePx - imageInsetPx + (textToIconPaddingPx / 2f)
    val arcRight = boxSizePx - imageInsetPx + (textToIconPaddingPx / 2f)

    val vectorPainter = rememberVectorPainter(
        ImageVector.vectorResource(icon)
    )
    Canvas(
        modifier = Modifier
            .size(boxSize)
            .background(Color.Gray)
            .clickable {
                onClick()
            }
    ) {
        drawCircle(Color.LightGray, radius = imageSizePx / 2f)
        with(vectorPainter) {
            inset(
                horizontal = imageInsetPx,
                vertical = imageInsetPx
            ) {
                draw(Size(imageSizePx, imageSizePx))
            }
        }
        drawIntoCanvas {
            val path = Path().apply {
                addArc(arcLeft, arcTop, arcRight, arcBottom, 270f, 180f)
            }
            it.nativeCanvas.drawTextOnPath(
                text,
                path,
                0f,
                0f,
                Paint().apply {
                    textSize = labelSize.toPx()
                    textAlign = Paint.Align.CENTER
                }
            )
        }
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
        drawIntoCanvas {
            val textPadding = 250.dp.toPx()
            val arcHeight = 400.dp.toPx()
            val arcWidth = 300.dp.toPx()
            val path = Path().apply {
                addArc(0f, textPadding, arcWidth, arcHeight, 180f, 180f)
            }
            it.nativeCanvas.drawTextOnPath(
                "Curved Text with Jetpack Compose",
                path,
                0f,
                0f,
                Paint().apply {
                    textSize = 16.sp.toPx()
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
fun DrawGradientCircles() {
    val startAngle = 22.5f
    val angle1 = remember { Animatable(initialValue = 0f) }
    val angle2 = remember { Animatable(initialValue = 0f) }
    val angle3 = remember { Animatable(initialValue = 0f) }
    val density = LocalDensity.current.density
    val boxSize = 300f
    val circleStrokeWidth = 90f
    val outerCircleSize = (boxSize * density) - (circleStrokeWidth / 2 * density)
    Canvas(
        modifier = Modifier
            .size(Dp(boxSize))
    ) {
        drawRect(Color.White)
        rotate(-90f) {
            val offset1 = circleStrokeWidth / 2
            val size1 = outerCircleSize
            drawArc(
                Brush.sweepGradient(listOf(Color.Magenta, Color.Red)),
                startAngle,
                angle1.value,
                false,
                topLeft = Offset(offset1, offset1),
                size = Size(size1, size1),
                style = Stroke(circleStrokeWidth, 0f, StrokeCap.Round)
            )
            val offset2 = circleStrokeWidth + (circleStrokeWidth / 2)
            val size2 = outerCircleSize - (circleStrokeWidth * 2)
            drawArc(
                Brush.sweepGradient(listOf(Color.Green, Color.Yellow)),
                startAngle,
                angle2.value,
                false,
                topLeft = Offset(offset2, offset2),
                size = Size(size2, size2),
                style = Stroke(circleStrokeWidth, 0f, StrokeCap.Round)
            )
            val offset3 = (circleStrokeWidth * 2) + (circleStrokeWidth / 2)
            val size3 = outerCircleSize - (circleStrokeWidth * 4)
            drawArc(
                Brush.sweepGradient(listOf(Color.Cyan, Color.Blue)),
                startAngle,
                angle3.value,
                false,
                topLeft = Offset(offset3, offset3),
                size = Size(size3, size3),
                style = Stroke(circleStrokeWidth, 0f, StrokeCap.Round)
            )
        }
    }

    LaunchedEffect("Square") {
        while (isActive) {
            angle1.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutSlowInEasing,
                )
            )
            angle2.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing,
                )
            )
            angle3.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutLinearInEasing
                )
            )
            angle1.snapTo(0f)
            angle2.snapTo(0f)
            angle3.snapTo(0f)
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

@Preview(showBackground = true)
@Composable
fun PreviewCanvasScreen() {
    MaterialTheme {
        CanvasScreen()
    }
}