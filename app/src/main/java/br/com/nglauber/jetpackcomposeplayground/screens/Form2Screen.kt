package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.view.SoundEffectConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun Form2Screen() {
    val textState = remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            PaddingSample()
            RotatedText()
            TextFieldWithMaxLength()
            OutlinedTextFieldBackground(Color.LightGray) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    label = {
                        Text(
                            text = "Name"
                        )
                    },
                )
            }
            AnnotatedTextSample()
            Switch(checked = enabled, onCheckedChange = { enabled = it })
            ButtonsSample(enabled)
            MarkdownText("Click [here](http://www.google.com) or http://www.google.com.")
            CustomShape()
        }
        Text(
            "Stack Text",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 16.dp)
        )
    }
}

@Composable
fun ButtonsSample(enabled: Boolean) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            enabled = enabled,
            content = { Text("Button", modifier = Modifier.padding(16.dp)) },
            onClick = {
                if (enabled) {
                    Log.d("NGVL", "Click")
                }
            }
        )
        OutlinedButton(
            content = { Text("OutlinedButton") },
            onClick = {
                val audioManager =
                    context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                audioManager.playSoundEffect(SoundEffectConstants.CLICK, 1.0f)
            }
        )
        TextButton(
            content = { Text("TextButton") },
            onClick = {}
        )
    }
}

@Composable
fun RotatedText() {
    Box(
        modifier = Modifier
            .graphicsLayer(
                rotationZ = -90f,
                translationX = 0f
            )
            .padding(vertical = 32.dp)
    ) {
        Text(
            "Element 1",
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier
                .background(color = Color.Red)
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}

@Composable
fun AnnotatedTextSample() {
    val builder = AnnotatedString.Builder().apply {
        append(
            AnnotatedString(
                "Texto Bold",
                SpanStyle(fontWeight = FontWeight.Bold)
            )
        )
        append(
            AnnotatedString(
                "Texto Strike",
                SpanStyle(textDecoration = TextDecoration.LineThrough)
            )
        )
        append(
            AnnotatedString(
                "Texto Big",
                SpanStyle(fontSize = 24.sp)
            )
        )
        append(
            AnnotatedString(
                "Texto Red",
                SpanStyle(color = Color.Red)
            )
        )
    }
    Text(text = builder.toAnnotatedString())
}

@Composable
fun PaddingSample() {
    val shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp)
//    val shape = RoundedCornerShape(8.dp)
//    val shape = CircleShape
    Text(
        text = "Text 1",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(2.dp, shape)
            .border(2.dp, MaterialTheme.colors.secondary, shape)
            .padding(1.dp)
            .background(MaterialTheme.colors.primary, shape)
            .clickable(onClick = {
                // Click event
            })
            .padding(16.dp)
    )
}

@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        content()
    }
}

@Composable
fun TextFieldWithMaxLength() {
    var nameState by remember { mutableStateOf("") }
    val maxLength = 20
    Column {
        OutlinedTextField(
            label = { Text("Digite seu nome") },
            value = nameState,
            onValueChange = { s: String ->
                if (s.length <= maxLength) {
                    nameState = s
                }
            }
        )
        Text(
            "${nameState.length} / $maxLength",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CustomShape() {
    Box(modifier = Modifier
        .size(50.dp, 50.dp)
        .border(
            width = 16.dp,
            color = Color.Red,
            shape = object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    return Outline.Generic(
                        // Just left border
                        Path().apply {
                            moveTo(0f, 0f)
                            lineTo(0f, size.height)
                            lineTo(16f, size.height)
                            lineTo(16f, 0f)
                            close()
                        }
                    )
                }
            }
        )
    )
}