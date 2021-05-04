package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalAnimationApi
@Composable
fun SlideInAnimationScreen() {
    val animationTime = 400
    var showScreen2 by remember {
        mutableStateOf(false)
    }
    val color = animateColorAsState(
        targetValue = if (showScreen2) Color.DarkGray else Color.Red,
        animationSpec = tween(
            durationMillis = animationTime,
            easing = LinearEasing
        )
    )
    Box(Modifier.fillMaxSize()) {
        // Screen 1
        AnimatedVisibility(
            !showScreen2,
            modifier = Modifier.fillMaxSize(),
            enter = slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(durationMillis = animationTime, easing = LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(durationMillis = animationTime, easing = LinearEasing)
            )
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color.value)
            ) {
                Button(modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        showScreen2 = true
                    }) {
                    Text(text = "Ok")
                }
            }
        }
        // Screen 2
        AnimatedVisibility(
            showScreen2,
            modifier = Modifier.fillMaxSize(),
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime, easing = LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime, easing = LinearEasing)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            ) {
                Button(modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        showScreen2 = false
                    }) {
                    Text(text = "Back")
                }
            }
        }
    }
}