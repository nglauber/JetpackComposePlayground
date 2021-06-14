package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import kotlin.math.max
import kotlin.math.min

@Composable
fun MyInstagramScreen() {
    val images = remember {
        listOf(
            R.drawable.img_london,
            R.drawable.img_ba,
            R.drawable.img_paris,
            R.drawable.img_sfo,
            R.drawable.recife
        )
    }
    val stepCount = images.size
    var currentStep by remember {
        mutableStateOf(0)
    }
    var isPaused by remember {
        mutableStateOf(false)
    }
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val imageModifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        currentStep =
                            if (offset.x < maxWidth.value / 2) {
                                max(0, currentStep - 1)
                            } else {
                                min(stepCount - 1, currentStep + 1)
                            }
                        isPaused = false
                    },
                    onPress = {
                        try {
                            isPaused = true
                            awaitRelease()
                        } finally {
                            isPaused = false
                        }
                    },
                    onLongPress = {
                        // must be here to avoid call onTap
                        // for play/pause behavior
                    }
                )
            }
        Image(
            painter = painterResource(id = images[currentStep]),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = imageModifier
        )
        MyInstagramProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            stepCount = stepCount,
            stepDuration = 2_000,
            unselectedColor = Color.LightGray,
            selectedColor = Color.Blue,
            currentStep = currentStep,
            onStepChanged = { currentStep = it },
            isPaused = isPaused,
            onComplete = { /* TODO */ }
        )
    }
}

@Composable
fun MyInstagramProgressIndicator(
    modifier: Modifier = Modifier,
    stepCount: Int,
    stepDuration: Int,
    unselectedColor: Color,
    selectedColor: Color,

    currentStep: Int,
    onStepChanged: (Int) -> Unit,
    onComplete: () -> Unit,
    isPaused: Boolean = false
) {
    var currentStepState by remember(currentStep) {
        mutableStateOf(currentStep)
    }
    val progress = remember(currentStep) {
        Animatable(0f)
    }
    Row(modifier) {
        for (i in 0 until stepCount) {
            val stepProgress = when {
                i == currentStepState -> progress.value
                i > currentStepState -> 0f
                else -> 1f
            }
            LinearProgressIndicator(
                color = selectedColor,
                backgroundColor = unselectedColor,
                progress = stepProgress,
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
            )
        }
    }

    LaunchedEffect(isPaused, currentStep) {
        if (isPaused) {
            progress.stop()
        } else {
            for (i in currentStep until stepCount) {
                progress.animateTo(
                    1f,
                    animationSpec = tween(
                        durationMillis =
                        ((1f - progress.value) * stepDuration)
                            .toInt(),
                        easing = LinearEasing
                    )
                )
                if (currentStepState + 1 <= stepCount - 1) {
                    progress.snapTo(0f)
                    currentStepState += 1
                    onStepChanged(currentStepState)
                } else {
                    onComplete()
                }
            }
        }
    }
}