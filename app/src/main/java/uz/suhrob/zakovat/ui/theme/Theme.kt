package uz.suhrob.zakovat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = purple900,
    primaryVariant = purple900,
    secondary = green200,
    background = gray50,
    onBackground = gray900,
    surface = gray50,
    onSurface = gray900,
    onPrimary = gray50,
)

@Composable
fun ZakovatTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}