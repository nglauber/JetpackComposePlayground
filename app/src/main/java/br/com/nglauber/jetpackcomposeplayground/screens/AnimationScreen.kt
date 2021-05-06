package br.com.nglauber.jetpackcomposeplayground.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.animatedVectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Source: https://slides.com/wajahatkarim/composeanimations#/title
@Composable
fun ExpandableText() {
    val shortText = "Click me"
    val longText = """"Very long text passage that spans
    across multiple lines, paragraphs
    and pages"""
    var short by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .background(
                Color.Blue,
                RoundedCornerShape(15.dp)
            )
            .clickable { short = !short }
            .padding(20.dp)
            .wrapContentSize()
            .animateContentSize()
    ) {
        Text(
            if (short) {
                shortText
            } else {
                longText
            },
            style = TextStyle(color = Color.White)
        )
    }
}

@Composable
fun PortraitModeImage() {
    var portraitMode by remember { mutableStateOf(true) }
    Box(
        Modifier
            .clickable { portraitMode = !portraitMode }
            .sizeIn(maxWidth = 300.dp, maxHeight = 300.dp)
            .background(
                if (portraitMode) Color(0xFFfffbd0) else Color(0xFFe3ffd9)
            )
            .animateContentSize(
                animationSpec = tween(300, easing = LinearEasing),
                finishedListener = { startSize, endSize ->
                    Log.d("NGVL", "$startSize -> $endSize")
                }
            )
            .aspectRatio(if (portraitMode) 3 / 4f else 16 / 9f)
    ) {
        Text(
            if (portraitMode) {
                "3 : 4"
            } else {
                "16 : 9"
            },
            style = TextStyle(color = Color.Black)
        )
    }
}


@ExperimentalAnimationApi
@Composable
fun VisibilityAnimationFAB() {
    var expanded by remember { mutableStateOf(true) }
    FloatingActionButton(
        onClick = { expanded = !expanded },
    ) {
        Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
            Icon(
                imageVector = if (expanded) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedVisibility(
                expanded,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(modifier = Modifier.padding(start = 8.dp), text = "Like")
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun VisibilityAnimationFAB2() {
    var expanded by remember { mutableStateOf(true) }
    FloatingActionButton(
        onClick = { expanded = !expanded },
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .animateContentSize(
                    animationSpec = tween(80, easing = LinearEasing),
                )
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                modifier = Modifier.align(Alignment.CenterVertically),
                contentDescription = null,
            )
            AnimatedVisibility(
                expanded,
                modifier = Modifier.align(Alignment.CenterVertically),
                enter = slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 160, delayMillis = 80)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { 100 },
                    animationSpec = tween(durationMillis = 80)
                )
            ) {
                Text(modifier = Modifier.padding(start = 8.dp), text = "Like")
            }
        }
    }
}

@Composable
fun ScaleAndColorAnimation() {
    val enabled = remember { mutableStateOf(true) }
    val color = if (enabled.value) MaterialTheme.colors.primary
    else MaterialTheme.colors.secondary

    val height = if (enabled.value) 40.dp else 60.dp
    val width = if (enabled.value) 150.dp else 300.dp

    Button(
        onClick = { enabled.value = !enabled.value },
        modifier = Modifier
            .background(animateColorAsState(color).value)
            .padding(16.dp)
            .height(animateDpAsState(height).value)
            .width(animateDpAsState(width).value),
    ) {
        Text("Scale & Color")
    }
}

@Composable
fun GenderSelectAnimation() {
    val female = remember { mutableStateOf(true) }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                R.drawable.male
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(animateDpAsState(if (female.value) 100.dp else 250.dp).value)
                .border(
                    width = animateDpAsState(if (female.value) 0.dp else 4.dp).value,
                    color = animateColorAsState(if (female.value) Color.Transparent else Color.Red).value
                )
                .padding(8.dp)
                .clickable { female.value = !female.value }
                .clip(RoundedCornerShape(animateDpAsState(if (female.value) 0.dp else 8.dp).value))
        )
        Image(
            painter = painterResource(R.drawable.female),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(animateDpAsState(if (!female.value) 100.dp else 250.dp).value)
                .border(
                    width = animateDpAsState(if (!female.value) 0.dp else 4.dp).value,
                    color = animateColorAsState(if (!female.value) Color.Transparent else Color.Red).value
                )
                .padding(8.dp)
                .clickable { female.value = !female.value }
                .clip(RoundedCornerShape(animateDpAsState(if (!female.value) 0.dp else 8.dp).value))
        )
    }
}


@Composable
fun HeartBeatDemo() {
    val animScale = remember { Animatable(initialValue = 1f) }
    val animColor = remember { Animatable(initialValue = Color.Red) }

    LaunchedEffect(Unit) {
        animScale.animateTo(
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300,
                    easing = FastOutLinearInEasing,
                    delayMillis = 1000
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        animColor.animateTo(
            targetValue = Color.Blue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300,
                    easing = LinearEasing,
                    delayMillis = 1000
                )
            )
        )
    }

    Image(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        modifier = Modifier
            .padding(10.dp)
            .size((40 * animScale.value).dp),
        colorFilter = ColorFilter.tint(animColor.value)
    )
}

@Composable
fun LineAnimation() {
    var lives by remember {
        mutableStateOf(0)
    }
    val animVal = remember { Animatable(0f) }
    if (lives > 5) {
        LaunchedEffect(animVal) {
            animVal.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
    Column {
        Button(onClick = { lives++ }) {
            Text("Count $lives")
        }
        Canvas(modifier = Modifier.size(200.dp, 200.dp)) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(animVal.value * size.width, animVal.value * size.height),
                strokeWidth = 2f
            )
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun AnimatedVectorDrawableAnim() {
    val image = animatedVectorResource(R.drawable.avd_anim)
    var atEnd by remember { mutableStateOf(false) }
    var isRunning by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    suspend fun runAnimation() {
        while (isRunning) {
            delay(1000)
            atEnd = !atEnd
        }
    }
    LaunchedEffect(image) {
        runAnimation()
    }
    Image(
        painter = image.painterFor(atEnd),
        null,
        Modifier
            .size(150.dp)
            .clickable {
                isRunning = !isRunning
                if (isRunning)
                    scope.launch {
                        runAnimation()
                    }
            },
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(Color.Red)
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AnimationScreen() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ExpandableText()
        PortraitModeImage()
        VisibilityAnimationFAB()
        VisibilityAnimationFAB2()
        ScaleAndColorAnimation()
        GenderSelectAnimation()
        LineAnimation()
        HeartBeatDemo()
        AnimatedVectorDrawableAnim()
    }
}