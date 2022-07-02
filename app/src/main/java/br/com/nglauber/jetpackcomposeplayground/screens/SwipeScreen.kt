package br.com.nglauber.jetpackcomposeplayground.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.nglauber.jetpackcomposeplayground.R
import kotlin.math.roundToInt

// Source: https://www.answertopia.com/jetpack-compose/detecting-swipe-gestures-in-jetpack-compose/
@ExperimentalMaterialApi
@Composable
fun SwipeableSampleScreen1() {
    val childBoxSides = 30.dp

    val swipeableState = rememberSwipeableState("L")

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val widthPx = with(LocalDensity.current) {
            (maxWidth - childBoxSides).toPx()
        }
        val anchors = mapOf(0f to "L", widthPx / 2 to "C", widthPx to "R")
        Box(
            modifier = Modifier
                .padding(20.dp)
                .width(maxWidth)
                .height(childBoxSides)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            // Line
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.DarkGray)
                    .align(Alignment.CenterStart)
            )
            // Left
            Box(
                Modifier
                    .size(10.dp)
                    .background(
                        Color.DarkGray,
                        shape = CircleShape
                    )
                    .align(Alignment.CenterStart)
            )
            // Center
            Box(
                Modifier
                    .size(10.dp)
                    .background(
                        Color.DarkGray,
                        shape = CircleShape
                    )
                    .align(Alignment.Center)
            )
            // Right
            Box(
                Modifier
                    .size(10.dp)
                    .background(
                        Color.DarkGray,
                        shape = CircleShape
                    )
                    .align(Alignment.CenterEnd)
            )
            // Indicator
            Box(
                Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .size(childBoxSides)
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    swipeableState.currentValue,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableSampleScreen2() {
    val swipeableState = rememberSwipeableState("L")

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val widthPx = with(LocalDensity.current) {
            maxWidth.toPx()
        }
        val anchors = mapOf(0f to "R", widthPx to "L")
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                )
        ) {
            Log.d("NGVL", "offset: ${swipeableState.offset.value.roundToInt()}")

            // Left
            Box(
                Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .fillMaxSize()
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    swipeableState.currentValue,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }

            // Right
            Box(
                Modifier
                    .offset {
                        IntOffset(
                            -widthPx.roundToInt() + swipeableState.offset.value.roundToInt(),
                            0
                        )
                    }
                    .fillMaxSize()
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    swipeableState.currentValue,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableSampleScreen3() {
    val images = listOf(
        R.drawable.recife,
        R.drawable.dog,
        R.drawable.male,
        R.drawable.female,
    )

    val swipeableState = rememberSwipeableState(0)

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val widthPx = with(LocalDensity.current) {
            maxWidth.toPx()
        }
        val anchors = remember(images) {
            List(images.size) { index ->
                -(index * widthPx) to index
            }.toMap()
        }
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                )
        ) {
            Log.d("NGVL", "offset: ${swipeableState.offset.value.roundToInt()}")
            images.forEachIndexed { index, image ->
                Image(
                    painter = painterResource(id = image), contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset {
                            IntOffset(
                                index * widthPx.roundToInt() + swipeableState.offset.value.roundToInt(),
                                0
                            )
                        },
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableSampleScreen4() {
    val images = listOf(
        R.drawable.recife,
        R.drawable.dog,
        R.drawable.male,
        R.drawable.female,
    )
    val swipeableState = rememberSwipeableState(0)

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val widthPx = with(LocalDensity.current) {
            maxWidth.toPx()
        }
        val anchors = remember(images) {
            List(images.size) { index ->
                -(index * widthPx) to index
            }.toMap()
        }
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                )
        ) {
            val currentIndex = swipeableState.currentValue
            val nextIndex = swipeableState.progress.to
            Image(
                painter = painterResource(id = images[nextIndex]), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 1f
                    }
            )
            Image(
                painter = painterResource(id = images[currentIndex]), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 1f - swipeableState.progress.fraction
                    }
            )
        }
    }
}

// Solution for: https://stackoverflow.com/questions/72796503/how-to-implement-fade-in-fade-out-animation-in-horizontal-pager-android-compos
@ExperimentalMaterialApi
@Composable
fun SwipeableSampleScreen5() {
    val pages = listOf(
        596 to Color(203, 41, 61),
        801 to Color(107, 0, 141),
        492 to Color(40, 38, 149),
        718 to Color(56, 144, 244),
        550 to Color(60, 149, 159)
    )
    val swipeableState = rememberSwipeableState(0)

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val widthPx = with(LocalDensity.current) {
            maxWidth.toPx()
        }
        val anchors = remember(pages) {
            List(pages.size) { index ->
                -(index * widthPx) to index
            }.toMap()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                )
        ) {
            val currentIndex = swipeableState.currentValue
            val nextIndex = swipeableState.progress.to
            Box(
                Modifier
                    .graphicsLayer {
                        alpha = 1f
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(pages[nextIndex].second),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pages[nextIndex].first.toString(),
                        style = MaterialTheme.typography.h1.copy(color = Color.White),
                    )
                }
            }
            Box(
                Modifier
                    .graphicsLayer {
                        alpha = 1f - swipeableState.progress.fraction
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(pages[currentIndex].second),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pages[currentIndex].first.toString(),
                        style = MaterialTheme.typography.h1.copy(color = Color.White),
                    )
                }
            }
        }
    }
}