package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import kotlin.math.min
import kotlin.math.sqrt

@Composable
fun HexagonShapeScreen() {
    Column {
        HexagonImgSquare()
        DodecagonImg()
        HexagonRoundedImg()
        HexagonCornerRoundedImg()
        HexagonImg2()
    }
}

@Composable
private fun ImgToDraw(myShape: Shape) {
    Image(
        painter = painterResource(id = R.drawable.dog),
        contentDescription = "My Hexagon Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(100.dp)
            .wrapContentWidth()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = myShape
                clip = true
            }
            .clip(myShape)
            .border(width = 2.dp, color = Color.Black, shape = myShape)
    )
}

@Composable
private fun HexagonImgSquare() {
    ImgToDraw(HexagonShape(type = 0))
}

@Composable
private fun DodecagonImg() {
    ImgToDraw(HexagonShape(type = 1))
}

@Composable
private fun HexagonRoundedImg() {
    ImgToDraw(HexagonShape(type = 2))
}

@Composable
private fun HexagonCornerRoundedImg() {
    ImgToDraw(HexagonShape(type = 3, cornerSize = 8.dp))
}

private fun hexagonPath(type: Int, size: Size, cornerSize: Float): Path {
    return when (type) {
        0 -> Path().apply {
            // 6 sides
            val radius = min(size.width / 2f, size.height / 2f)
            val triangleHeight = (sqrt(3.0) * radius / 2)
            val centerX = size.width / 2
            val centerY = size.height / 2

            moveTo(centerX, centerY + radius) // A
            lineTo((centerX - triangleHeight).toFloat(), centerY + radius / 2) // B
            lineTo((centerX - triangleHeight).toFloat(), centerY - radius / 2) // C
            lineTo(centerX, centerY - radius) // D
            lineTo((centerX + triangleHeight).toFloat(), centerY - radius / 2) // E
            lineTo((centerX + triangleHeight).toFloat(), centerY + radius / 2) // F

            close()
        }

        1 -> Path().apply {
            // 12 sides
            val radius = min(size.width / 2f, size.height / 2f)
            val triangleHeight = (sqrt(3.0) * radius / 2)
            val centerX = size.width / 2
            val centerY = size.height / 2

            moveTo(centerX, centerY + radius) // A
            lineTo(centerX - radius / 2, (centerY + triangleHeight).toFloat()) // A.1
            lineTo((centerX - triangleHeight).toFloat(), centerY + radius / 2) // B
            lineTo(centerX - radius, centerY) // B.1
            lineTo((centerX - triangleHeight).toFloat(), centerY - radius / 2) // C
            lineTo(centerX - radius / 2, (centerY - triangleHeight).toFloat()) // C.1
            lineTo(centerX, centerY - radius) // D
            lineTo(centerX + radius / 2, (centerY - triangleHeight).toFloat()) // D.1
            lineTo((centerX + triangleHeight).toFloat(), centerY - radius / 2) // E
            lineTo(centerX + radius, centerY) // E.1
            lineTo((centerX + triangleHeight).toFloat(), centerY + radius / 2) // F
            lineTo(centerX + radius / 2, (centerY + triangleHeight).toFloat()) // F.1
            close()
        }

        2 -> Path().apply {
            // 6 sides quadratic bezier
            val radius = min(size.width / 2f, size.height / 2f)
            val triangleHeight = (sqrt(3.0) * radius / 2)
            val centerX = size.width / 2
            val centerY = size.height / 2

            moveTo(centerX + radius / 2, (centerY + triangleHeight).toFloat()) // F.1
            quadraticBezierTo(
                centerX, centerY + radius, // A
                centerX - radius / 2, (centerY + triangleHeight).toFloat(), // A.1
            )
            quadraticBezierTo(
                (centerX - triangleHeight).toFloat(), centerY + radius / 2, // B
                centerX - radius, centerY // B.1
            )
            quadraticBezierTo(
                (centerX - triangleHeight).toFloat(), centerY - radius / 2, // C
                centerX - radius / 2, (centerY - triangleHeight).toFloat(), // C.1
            )
            quadraticBezierTo(
                centerX, centerY - radius, // D
                centerX + radius / 2, (centerY - triangleHeight).toFloat() // D.1
            )
            quadraticBezierTo(
                (centerX + triangleHeight).toFloat(), centerY - radius / 2, // E
                centerX + radius, centerY // E.1
            )
            quadraticBezierTo(
                (centerX + triangleHeight).toFloat(), centerY + radius / 2, // F
                centerX + radius / 2, (centerY + triangleHeight).toFloat(), // F.1
            )
            close()
        }

        3 -> Path().apply {
            val radius = min(size.width / 2f, size.height / 2f)
            val triangleHeight = (sqrt(3.0) * radius / 2)
            val centerX = size.width / 2
            val centerY = size.height / 2
            val midRound = cornerSize / 2f

            // A
            moveTo(centerX + cornerSize, centerY + radius - midRound)
            quadraticBezierTo(
                centerX, centerY + radius,
                centerX - cornerSize, centerY + radius - midRound
            )
            // B
            lineTo((centerX - triangleHeight).toFloat() + midRound, centerY + radius / 2 + midRound)
            quadraticBezierTo(
                (centerX - triangleHeight).toFloat(), centerY + radius / 2,
                (centerX - triangleHeight).toFloat(), centerY + radius / 2 - midRound,
            )
            // C
            lineTo((centerX - triangleHeight).toFloat(), centerY - radius / 2 + midRound) // C
            quadraticBezierTo(
                (centerX - triangleHeight).toFloat(), centerY - radius / 2,
                (centerX - triangleHeight).toFloat() + midRound, centerY - radius / 2 - midRound
            )
            // D
            lineTo(centerX - cornerSize, centerY - radius + midRound) // D
            quadraticBezierTo(
                centerX, centerY - radius,
                centerX + cornerSize, centerY - radius + midRound,
            )
            // E
            lineTo(
                (centerX + triangleHeight).toFloat() - midRound,
                centerY - radius / 2 - midRound
            )
            quadraticBezierTo(
                (centerX + triangleHeight).toFloat(), centerY - radius / 2,
                (centerX + triangleHeight).toFloat(), centerY - radius / 2 + midRound
            )
            // F
            lineTo((centerX + triangleHeight).toFloat(), centerY + radius / 2 - midRound) // F
            quadraticBezierTo(
                (centerX + triangleHeight).toFloat(), centerY + radius / 2,
                (centerX + triangleHeight).toFloat() - midRound, centerY + radius / 2 + midRound
            )
            close()
        }

        else -> Path()
    }
}

private class HexagonShape(
    private val type: Int,
    private val cornerSize: Dp = 0.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = hexagonPath(type, size, with(density) { cornerSize.toPx() })
        )
    }
}

// Simple solution
@Composable
private fun HexagonImg2() {
    val borderWidth = 4.dp
    val cornerSize = 8.dp
    val myShape = HexagonShape2()
    Box(modifier = Modifier
        .drawWithContent {
            drawContent()
            drawPath(
                path = drawCustomHexagonPath(size),
                color = Color.Red,
                style = Stroke(
                    width = borderWidth.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(cornerSize.toPx())
                )
            )
        }
        .wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "My Hexagon Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    shadowElevation = 8.dp.toPx()
                    shape = myShape
                    clip = true
                }
                .background(color = Color.Cyan)
        )
    }
}

class HexagonShape2 : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawCustomHexagonPath(size)
        )
    }
}

fun drawCustomHexagonPath(size: Size): Path {
    return Path().apply {
        val radius = min(size.width / 2f, size.height / 2f)
        customHexagon(radius, size)
    }
}

fun Path.customHexagon(radius: Float, size: Size) {
    val triangleHeight = (sqrt(3.0) * radius / 2)
    val centerX = size.width / 2
    val centerY = size.height / 2

    moveTo(centerX, centerY + radius)
    lineTo((centerX - triangleHeight).toFloat(), centerY + radius / 2)
    lineTo((centerX - triangleHeight).toFloat(), centerY - radius / 2)
    lineTo(centerX, centerY - radius)
    lineTo((centerX + triangleHeight).toFloat(), centerY - radius / 2)
    lineTo((centerX + triangleHeight).toFloat(), centerY + radius / 2)

    close()
}