package br.com.nglauber.jetpackcomposeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200
)

private val LightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun JetpackComposePlaygroundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val textColor = if (darkTheme) Color.White else Color.Black
    val reversedTextColor = if (darkTheme) Color.Black else Color.White

    MaterialTheme(
        colors = colors,
        typography = typography.copy(
            h1 = typography.h1.copy(
                color = textColor
            ),
            h2 = typography.h2.copy(
                color = textColor
            ),
            h3 = typography.h3.copy(
                color = textColor
            ),
            h4 = typography.h4.copy(
                color = textColor
            ),
            h5 = typography.h5.copy(
                color = textColor
            ),
            h6 = typography.h6.copy(
                color = textColor
            ),
            subtitle1 = typography.subtitle1.copy(
                color = textColor
            ),
            subtitle2 = typography.subtitle2.copy(
                color = textColor
            ),
            body1 = typography.body1.copy(
                color = textColor
            ),
            body2 = typography.body2.copy(
                color = textColor
            ),
            caption = typography.caption.copy(
                color = textColor
            ),
            button = typography.button.copy(
                color = reversedTextColor
            ),
            overline = typography.overline.copy(
                color = textColor
            )
        ),
        content = content
    )
}