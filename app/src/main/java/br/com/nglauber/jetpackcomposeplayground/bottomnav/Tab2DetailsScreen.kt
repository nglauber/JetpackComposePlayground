package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Tab2DetailsScreen(device: Device, paddingValues: PaddingValues) {
    Column(Modifier.padding(paddingValues)) {
        Text(text = "Tab 2 - Details - $device")
        ConstraintLayoutWeightDemo()
    }
}

@Composable
fun ConstraintLayoutWeightDemo() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (refB1, refB2, refB3) = createRefs()
        Text(
            "Text 1",
            modifier = Modifier
                .constrainAs(refB1) {
                    start.linkTo(parent.start)
                    end.linkTo(refB2.start)
                }
                .fillMaxWidth(.3f)
                .background(Color.Red)
        )
        Text(
            "Text 2",
            modifier = Modifier
                .constrainAs(refB2) {
                    start.linkTo(refB1.end)
                    end.linkTo(refB3.start)
                }
                .fillMaxWidth(.5f)
                .background(Color.Green)
        )
        Text(
            "Text 3",
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