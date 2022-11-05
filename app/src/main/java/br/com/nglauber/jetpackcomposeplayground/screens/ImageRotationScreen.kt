package br.com.nglauber.jetpackcomposeplayground.screens

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.util.drawableId
import br.com.nglauber.jetpackcomposeplayground.util.graphicsRotation
import coil.compose.AsyncImagePainter.State.Empty.painter
import kotlin.math.atan2

// https://stackoverflow.com/questions/72603132/rotate-image-with-single-finger-using-compose/72653282#72653282
@ExperimentalComposeUiApi
@Composable
fun OneFingerImageRotationScreen() {
    var viewRotation = remember { 0.0 }
    var fingerRotation = remember { 0.0 }
    var rotation by remember {
        mutableStateOf(0.0)
    }
    val density = LocalDensity.current
    BoxWithConstraints {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .pointerInteropFilter { event ->
                    val x: Float = event.x
                    val y: Float = event.y
                    val xc: Float = with(density) { maxWidth.toPx() } / 2f
                    val yc: Float = with(density) { maxHeight.toPx() } / 2f

                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            viewRotation = rotation
                            fingerRotation =
                                Math.toDegrees(atan2((x - xc).toDouble(), (yc - y).toDouble()))
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val newFingerRotation =
                                Math.toDegrees(atan2((x - xc).toDouble(), (yc - y).toDouble()))
                            rotation = (viewRotation + newFingerRotation - fingerRotation)
                        }

                        MotionEvent.ACTION_UP -> {
                            fingerRotation = 0.0
                        }
                    }
                    true
                }
        ) {
            val imageRes = R.drawable.dog
            Image(
                modifier = Modifier
                    .semantics {
                        drawableId = imageRes
                        graphicsRotation = rotation.toFloat()
                    }
                    .align(Alignment.Center)
                    .size(200.dp)
                    .graphicsLayer {
                        rotationZ = rotation.toFloat()
                    },
                contentDescription = null,
                painter = painterResource(imageRes)
            )
        }
    }
}