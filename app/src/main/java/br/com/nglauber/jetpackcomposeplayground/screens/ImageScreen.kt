package br.com.nglauber.jetpackcomposeplayground.screens

import android.graphics.*
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.zoomable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import br.com.nglauber.jetpackcomposeplayground.R

@Composable
fun ImageScreen() {
    Column(Modifier.fillMaxSize()) {
        RoundedImage()
        GrayscaleImage()
        GrayscaleDrawable()
        ZoomableImage()
        //ZoomAndTranslateImage()
    }
}

@Composable
fun RoundedImage() {
    Image(
        bitmap = imageFromResource(LocalContext.current.resources, R.drawable.dog),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .preferredSize(128.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun GrayscaleImage() {
    val context = LocalContext.current.resources
    val image = remember {
        val source = imageFromResource(context, R.drawable.dog)
        toGrayscale(
            source.asAndroidBitmap()
        ).asImageBitmap()
    }
    Image(
        bitmap = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .preferredSize(128.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun GrayscaleDrawable() {
    val context = LocalContext.current
    val image = remember {
        val drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
        toGrayscale(
            drawable?.toBitmap()!!
        ).asImageBitmap()
    }
    Image(image, null, contentScale = ContentScale.FillHeight)
}

fun toGrayscale(bmpOriginal: Bitmap): Bitmap {
    val width: Int = bmpOriginal.width
    val height: Int = bmpOriginal.height
    val bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmpGrayscale)
    val paint = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    val f = ColorMatrixColorFilter(cm)
    paint.colorFilter = f
    c.drawBitmap(bmpOriginal, 0f, 0f, paint)
    return bmpGrayscale
}

@Composable
fun ZoomableImage() {
    val scale = remember { mutableStateOf(1f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize() // Give the size you want...
            .background(Color.Gray)
            .zoomable(onZoomDelta = { scale.value *= it })
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(2f, scale.value)),
                    scaleY = maxOf(.5f, minOf(2f, scale.value))
                ),
            contentDescription = null,
            painter = painterResource(R.drawable.dog)
        )
    }
}

@Composable
fun ZoomAndTranslateImage() {
    var scale by remember { mutableStateOf(1f) }
    var translate by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .zoomable(onZoomDelta = { scale *= it })
            .pointerInput("a") {
                awaitPointerEventScope {
                    currentEvent.changes.getOrNull(0)?.let {
                        translate = translate.plus(it.position)
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.recife),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()                .graphicsLayer(
                // adding some zoom limits (min 50%, max 200%)
                scaleX = maxOf(.5f, minOf(2f, scale)),
                scaleY = maxOf(.5f, minOf(2f, scale)),
                translationX = translate.x,
                translationY = translate.y
            ),
        )
    }
}