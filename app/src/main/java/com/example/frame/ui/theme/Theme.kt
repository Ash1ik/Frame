package com.example.frame.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = FramePrimary,
    onPrimary = FrameOnPrimary,
    primaryContainer = FramePrimaryContainer,
    onPrimaryContainer = FrameOnPrimaryContainer,

    secondary = FrameSecondary,
    onSecondary = FrameOnSecondary,
    secondaryContainer = FrameSecondaryContainer,
    onSecondaryContainer = FrameOnSecondaryContainer,

    background = FrameBackground,
    onBackground = FrameOnBackground,

    surface = FrameSurface,
    onSurface = FrameOnSurface,
    surfaceVariant = FrameSurfaceVariant,
    onSurfaceVariant = FrameOnSurfaceVariant,

    outline = FrameOutline,
    error = FrameError,
    onError = FrameOnError

)

@Composable
fun FrameTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}