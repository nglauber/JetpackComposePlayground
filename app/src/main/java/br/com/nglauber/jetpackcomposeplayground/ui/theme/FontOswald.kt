package br.com.nglauber.jetpackcomposeplayground.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import br.com.nglauber.jetpackcomposeplayground.R

val appFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.oswald_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.oswald_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.oswald_semi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.oswald_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.oswald_light,
            weight = FontWeight.Light,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.oswald_extra_light,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Normal
        )
    )
)