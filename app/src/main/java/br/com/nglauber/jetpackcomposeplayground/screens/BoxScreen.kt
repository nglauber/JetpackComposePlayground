package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun BoxScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.recife),
            contentDescription = "Recife",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            rememberGlidePainter(
                request = "https://pbs.twimg.com/profile_images/836560780422164480/vuClsC2w_400x400.jpg",
            ),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .align(Alignment.BottomStart)
        )
        Text(
            "Recife - PE",
            modifier = Modifier
                .background(Color.Black)
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            color = Color.White
        )
        Image(
            painter = painterResource(R.drawable.ic_android_orange),
            contentDescription = "Android",
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.TopEnd)
                .size(100.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Cyan)
        )
    }
}