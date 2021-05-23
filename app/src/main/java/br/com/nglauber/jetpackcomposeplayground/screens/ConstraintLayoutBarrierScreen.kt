package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutBarrierScreen() {
    val itemsCount = 20
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val textNumRefs = (1..itemsCount).map { createRef() }
        val textDescriptionRefs = (1..itemsCount).map { createRef() }
        val barrier = createEndBarrier(*textNumRefs.toTypedArray())
        textNumRefs.forEachIndexed { index, _ ->
            Text(
                text = "${index + 1}.",
                Modifier
                    .constrainAs(textNumRefs[index]) {
                        top.linkTo(
                            if (index == 0)
                                parent.top
                            else
                                textDescriptionRefs[index - 1].bottom
                        )
                        start.linkTo(parent.start)
                        if (index < textNumRefs.lastIndex) {
                            end.linkTo(textNumRefs[index + 1].end)
                        }
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 6.dp),
                textAlign = TextAlign.End
            )
            Text(text = "A long text that I expect that has more than one line of text because I want to test that.",
                Modifier.constrainAs(textDescriptionRefs[index]) {
                    top.linkTo(
                        if (index == 0)
                            parent.top
                        else
                            textDescriptionRefs[index - 1].bottom
                    )
                    start.linkTo(barrier)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}