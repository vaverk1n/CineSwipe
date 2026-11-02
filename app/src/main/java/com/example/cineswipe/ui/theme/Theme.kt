package com.example.cineswipe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colors = DarkCineColors
    val typography = CineTypography()
    val shapes = CineShapes()

    val materialColors = darkColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.background,
        onSurface = colors.onBackground,
        onSurfaceVariant = colors.onBackgroundMuted,
        error = colors.nope,
        onError = colors.background,
    )

    CompositionLocalProvider(
        LocalCineColors provides colors,
        LocalCineTypography provides typography,
        LocalCineShapes provides shapes,
        LocalSpacing provides Spacing(),
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = typography.toMaterial(),
            shapes = shapes.toMaterial(),
            content = content,
        )
    }
}

/** Доступ к токенам: `AppTheme.colors.want`, `AppTheme.spacing.l`. */
object AppTheme {
    val colors: CineColors
        @Composable @ReadOnlyComposable get() = LocalCineColors.current
    val typography: CineTypography
        @Composable @ReadOnlyComposable get() = LocalCineTypography.current
    val shapes: CineShapes
        @Composable @ReadOnlyComposable get() = LocalCineShapes.current
    val spacing: Spacing
        @Composable @ReadOnlyComposable get() = LocalSpacing.current
}