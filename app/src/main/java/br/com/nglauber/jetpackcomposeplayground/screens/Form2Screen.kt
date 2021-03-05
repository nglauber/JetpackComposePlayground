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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Form2Screen() {
    val context = LocalContext.current
    val shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp)
//    val shape = RoundedCornerShape(8.dp)
//    val shape = CircleShape
    var nameState by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
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
            TextSample()
            Switch(checked = enabled, onCheckedChange = { enabled = it })
            TextField(
                label = { Text("Digite seu nome") },
                value = nameState,
                onValueChange = { s: String ->
                    nameState = s
                }
            )
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
        Text(
            "Stack Text",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 16.dp)
        )
    }
}

@Composable
fun TextSample() {
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