package br.com.smdevelopment.rastreamentocorreios.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = primary700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = primary500,
    primaryVariant = primary700,
    secondary = Teal200
)

@Composable
fun RastreamentoCorreiosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //TODO: Implement dark mode.
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}