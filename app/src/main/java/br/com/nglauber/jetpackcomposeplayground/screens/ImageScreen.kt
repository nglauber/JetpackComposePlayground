package br.com.nglauber.jetpackcomposeplayground.screens

import android.graphics.*
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.imageloading.rememberDrawablePainter


@Composable
fun ImageScreen() {
    Column(Modifier.fillMaxSize()) {
        Row {
            RoundedImage()
            GrayscaleImage()
        }
        Row {
            GrayscaleDrawable()
            NinePatchImage()
        }
        ZoomableImage()
//        ZoomAndTranslateImage()
    }
}

@Composable
fun RoundedImage() {
    Image(
        bitmap = ImageBitmap.imageResource(R.drawable.dog),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun GrayscaleImage() {
    Image(
        bitmap = ImageBitmap
            .imageResource(R.drawable.dog),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(128.dp)
            .clip(RoundedCornerShape(8.dp)),
        colorFilter = ColorFilter.colorMatrix(
            androidx.compose.ui.graphics.ColorMatrix().apply {
                setToSaturation(0f)
            }
        )
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
    val rotationState = remember { mutableStateOf(1f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize() // Give the size you want...
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                    rotationZ = rotationState.value
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
            .transformable(state = TransformableState { zoomChange, panChange, rotationChange ->
                scale *= zoomChange
                translate += panChange
            })
    ) {
        Image(
            painter = painterResource(R.drawable.recife),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(2f, scale)),
                    scaleY = maxOf(.5f, minOf(2f, scale)),
                    translationX = translate.x,
                    translationY = translate.y
                ),
        )
    }
}

@Composable
fun NinePatchImage() {
    val context = LocalContext.current
    Image(
        rememberDrawablePainter(
            drawable = ContextCompat.getDrawable(context, R.drawable.balao)
        ),
        contentDescription = "Faq card 1",
        Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}