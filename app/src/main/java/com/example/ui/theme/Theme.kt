package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val VelvetColorScheme = darkColorScheme(
    primary = VelvetPrimary,
    onPrimary = VelvetOnPrimary,
    primaryContainer = VelvetPrimaryContainer,
    onPrimaryContainer = VelvetOnPrimaryContainer,
    secondary = VelvetSecondary,
    onSecondary = VelvetOnSecondary,
    secondaryContainer = VelvetSecondaryContainer,
    onSecondaryContainer = VelvetOnSecondaryContainer,
    tertiary = VelvetTertiary,
    onTertiary = VelvetOnTertiary,
    tertiaryContainer = VelvetTertiaryContainer,
    onTertiaryContainer = VelvetOnTertiaryContainer,
    background = VelvetSurface,
    onBackground = VelvetOnSurface,
    surface = VelvetSurface,
    onSurface = VelvetOnSurface,
    surfaceVariant = VelvetSurfaceContainer,
    onSurfaceVariant = VelvetOnSurfaceVariant,
    outline = VelvetOutline,
    outlineVariant = VelvetOutlineVariant,
    error = VelvetError,
    onError = VelvetOnError,
    errorContainer = VelvetErrorContainer,
    onErrorContainer = VelvetOnErrorContainer
)

@Composable
fun VelvetTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                @Suppress("DEPRECATION")
                window.statusBarColor = VelvetSurface.toArgb()
                @Suppress("DEPRECATION")
                window.navigationBarColor = VelvetSurface.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = VelvetColorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for template backwards compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    VelvetTheme(content = content)
}
