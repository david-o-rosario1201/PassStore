package edu.ucne.passstore.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val LightColors = lightColorScheme(
        primary = Color(0xFF0D47A1),
        onPrimary = Color.White,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
    )

    val DarkColors = darkColorScheme(
        primary = Color(0xFF90CAF9),
        onPrimary = Color.Black,
        background = Color.Black,
        onBackground = Color.White,
        surface = Color(0xFF1E1E1E),
        onSurface = Color.White,
    )

    val colors = if (isDarkMode) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}