package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.media.AudioManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
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
    var multilineTextState by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
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
            TextFieldWithVisualTransformation()
            AnnotatedTextSample()
            Switch(checked = enabled, onCheckedChange = { enabled = it })
            ButtonsSample(enabled)
            MarkdownText("Click [here](http://www.google.com) or http://www.google.com.")
            CustomShape()
            MySlider()
            MultilineTextFieldSample(
                text = multilineTextState,
                onTextChanged = { multilineTextState = it },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
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
fun MySlider() {
    val maxValue = 5
    var value by remember { mutableStateOf(0f) }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Slider(value = value, onValueChange = { value = it })
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..maxValue) {
                Text(i.toString())
            }
        }
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
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
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
    val arcHeight = with(LocalDensity.current) { 30.dp.toPx() }
    Box(modifier = Modifier
        .size(100.dp, 100.dp)
        .background(
            color = Color.Red,
            shape = object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    return Outline.Generic(
                        Path().apply {
                            moveTo(0f, 0f)
                            lineTo(0f, size.height - arcHeight)
                            arcTo(
                                Rect(
                                    0f,
                                    size.height - arcHeight,
                                    size.width,
                                    size.height
                                ), 180f, -180f, false
                            )
                            lineTo(size.width, size.height - arcHeight)
                            lineTo(size.width, 0f)
                            close()
                        }
                    )
                }
            }
        )
    )
}

@Composable
private fun TextFieldWithVisualTransformation() {
    var text by remember {
        mutableStateOf("")
    }
    TextField(
        value = text,
        onValueChange = { text = it },
        visualTransformation = PhoneTransformation("XX-X-XXXX-XXXX", "+55 "),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

class PhoneTransformation(
    private val mask: String,
    private val prefix: String = "",
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return prefixFilter(text, prefix, mask)
    }

    private fun prefixFilter(
        number: AnnotatedString,
        prefix: String,
        format: String
    ): TransformedText {

        val transformedNumber = StringBuffer()
        var spaceCount = 0

        number.text.forEachIndexed { index, c ->
            if (index + spaceCount < format.length && format[index + spaceCount] == '-') {
                spaceCount++
                transformedNumber.append(' ')
            }
            transformedNumber.append(c)
        }

        val out = prefix + transformedNumber
        val prefixOffset = prefix.length
        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + prefixOffset + spaceCount
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= prefixOffset - 1) return prefixOffset
                return offset - prefixOffset - spaceCount
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}

object MultilineText

@Composable
fun MultilineTextFieldSample(
    text: String,
    onTextChanged: (String) -> Unit,
    minLines: Int,
    modifier: Modifier = Modifier
) {
    val localDensity = LocalDensity.current
    SubcomposeLayout { constraints ->
        val subcomp = subcompose(MultilineText) {
            // Measuring the max height
            val fakeText = (1 until minLines).joinToString { "\n" }
            OutlinedTextField(fakeText, onValueChange = {})
        }
        val placeable = subcomp.first().measure(constraints)
        val height = placeable.height
        val heightInDp = with(localDensity) { ((height + 1) / density).dp }

        // Measure the actual content
        val placeables = subcompose(ContentPlaceables) {
            // This is the actual content, but now we know the maximum height
            OutlinedTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = modifier.heightIn(min = heightInDp),
                maxLines = minLines
            )
        }
        val contentPlaceable = placeables
            .first()
            .measure(constraints)

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}