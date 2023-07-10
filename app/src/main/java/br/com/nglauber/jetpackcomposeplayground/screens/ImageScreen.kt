package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.util.IdlingThreadPool
import br.com.nglauber.jetpackcomposeplayground.util.coilAsyncPainter
import br.com.nglauber.jetpackcomposeplayground.util.drawableId
import br.com.nglauber.jetpackcomposeplayground.util.graphicsLayerScale
import br.com.nglauber.jetpackcomposeplayground.util.graphicsRotation
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.asCoroutineDispatcher

@ExperimentalCoilApi
@Composable
fun ImageScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            RoundedImage()
            GrayscaleImage()
        }
        Row {
            GrayscaleDrawable()
            NinePatchImage()
        }
        ZoomableImage(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row {
            SvgImageSample()
            SvgLocalImageSample()
        }
//        ZoomAndTranslateImage()
    }
}

@Composable
fun SvgImageSample() {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .dispatcher(IdlingThreadPool.asCoroutineDispatcher())
            .decoderFactory(SvgDecoder.Factory())
            .data("https://upload.wikimedia.org/wikipedia/commons/d/d7/Android_robot.svg")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    Image(
        painter = painter,
        modifier = Modifier
            .size(100.dp)
            .semantics {
                testTag = ImageScreenAsyncImageTestTag
                coilAsyncPainter = painter
            },
        contentDescription = null
    )
}

@Composable
fun SvgLocalImageSample() {
    val ctx = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(ctx)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${ctx.applicationContext.packageName}/${R.raw.android_robot}")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    Image(
        painter = painter,
        modifier = Modifier.size(100.dp),
        contentDescription = null
    )
}

@Composable
fun RoundedImage() {
    val imageRes = R.drawable.dog
    Image(
        bitmap = ImageBitmap.imageResource(imageRes),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .semantics { drawableId = imageRes }
            .size(128.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun GrayscaleImage() {
    val imageRes = R.drawable.img_ba
    Image(
        bitmap = ImageBitmap.imageResource(imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .semantics { drawableId = imageRes }
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
    val imageRes = R.mipmap.ic_launcher
    val context = LocalContext.current
    val image = remember {
        val drawable = ContextCompat.getDrawable(context, imageRes)
        toGrayscale(
            drawable?.toBitmap()!!
        ).asImageBitmap()
    }
    Image(
        image,
        null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.semantics { drawableId = imageRes })
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
fun ZoomableImage(modifier: Modifier) {
    var scaleState by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(1f) }
    Box(
        modifier = modifier
            .testTag(ImageScreenZoomableContainerTestTag)
            .clip(RectangleShape) // Clip the box content
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, rotation ->
                    scaleState *= zoom
                    rotationState += rotation
                }
            }
    ) {
        Image(
            modifier = Modifier
                .testTag(ImageScreenZoomableImageTestTag)
                .semantics {
                    graphicsLayerScale = scaleState
                    graphicsRotation = rotationState
                }
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer {
                    // adding some zoom limits (min 50%, max 300%)
                    scaleX = maxOf(.5f, minOf(3f, scaleState))
                    scaleY = maxOf(.5f, minOf(3f, scaleState))
                    rotationZ = rotationState
                },
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
            .transformable(state = TransformableState { zoomChange, panChange, _ ->
                scale *= zoomChange
                translate += panChange
            })
    ) {
        Image(
            painter = painterResource(R.drawable.recife),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(2f, scale))
                    scaleY = maxOf(.5f, minOf(2f, scale))
                    translationX = translate.x
                    translationY = translate.y
                },
        )
    }
}

fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes ninePatchRes: Int,
) = this.drawBehind {
    drawIntoCanvas {
        ContextCompat.getDrawable(context, ninePatchRes)?.let { ninePatch ->
            ninePatch.run {
                bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                draw(it.nativeCanvas)
            }
        }
    }
}

@Composable
fun NinePatchImage() {
    Box(
        Modifier.draw9Patch(LocalContext.current, R.drawable.balao)
    ) {
        Text(
            text = """
                |This is a long text to stretch the balloon
                |Diego, are you happy?
                |I know you are
                |Just one line for this
                |Yes!""".trimMargin("|"),
            Modifier
                .padding(
                    top = 24.dp,
                    bottom = 24.dp,
                    start = 32.dp,
                    end = 48.dp
                )
        )
    }
}

const val ImageScreenZoomableContainerTestTag = "ZoomableImageContainer"
const val ImageScreenZoomableImageTestTag = "ZoomableImage"
const val ImageScreenAsyncImageTestTag = "AsyncVectorImage"