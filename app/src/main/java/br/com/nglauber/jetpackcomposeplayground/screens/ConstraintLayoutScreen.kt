package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (text1Ref, edit1Ref, btn1Ref, btn2Ref) = createRefs()
        Text("Nome", modifier = Modifier.constrainAs(text1Ref) {
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)
        })
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.padding(top = 8.dp)
                .constrainAs(edit1Ref) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(text1Ref.bottom)
                })
        Button(onClick = {},
            content = { Text("OK") },
            modifier = Modifier.padding(top = 8.dp).constrainAs(btn1Ref) {
                end.linkTo(edit1Ref.end)
                top.linkTo(edit1Ref.bottom)
            }
        )
        TextButton(onClick = {},
            content = { Text("Cancel") },
            modifier = Modifier.padding(end = 8.dp)
                .constrainAs(btn2Ref) {
                    end.linkTo(btn1Ref.start)
                    baseline.linkTo(btn1Ref.baseline)
                }
        )
    }
}