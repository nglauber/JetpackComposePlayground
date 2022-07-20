package br.com.nglauber.jetpackcomposeplayground.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutScreen() {
    Column(Modifier.fillMaxSize()) {
        ConstraintLayoutForm()
        ConstraintLayoutWeightDemo()
    }
}

@Composable
private fun ConstraintLayoutForm() {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var text by remember {
        mutableStateOf("")
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (text1Ref, edit1Ref, btn1Ref, btn2Ref) = createRefs()
        Text(
            "Nome",
            Modifier.constrainAs(text1Ref) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
            }
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Digite seu nome") },
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(edit1Ref) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(text1Ref.bottom)
                })
        Button(
            onClick = {
                clipboardManager.setText(AnnotatedString("Some text"))
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(btn1Ref) {
                    end.linkTo(edit1Ref.end)
                    top.linkTo(edit1Ref.bottom)
                }
        ) {
            Text("Copy")
        }
        TextButton(
            onClick = {
                text = clipboardManager.getText()?.toString() ?: ""
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .constrainAs(btn2Ref) {
                    end.linkTo(btn1Ref.start)
                    baseline.linkTo(btn1Ref.baseline)
                }
        ) {
            Text("Paste")
        }
    }
}

@Composable
private fun ConstraintLayoutWeightDemo() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (refB1, refB2, refB3) = createRefs()
        Text(
            "30%",
            modifier = Modifier
                .constrainAs(refB1) {
                    start.linkTo(parent.start)
                    end.linkTo(refB2.start)
                }
                .fillMaxWidth(.3f)
                .background(Color.Red)
        )
        Text(
            "50%",
            modifier = Modifier
                .constrainAs(refB2) {
                    start.linkTo(refB1.end)
                    end.linkTo(refB3.start)
                }
                .fillMaxWidth(.5f)
                .background(Color.Green)
        )
        Text(
            "20%",
            modifier = Modifier
                .constrainAs(refB3) {
                    start.linkTo(refB2.end)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(.2f)
                .background(Color.Blue)
        )
    }
}