package com.example.cineswipe.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class CineColors(
    val background: Color,
    val onBackground: Color,
    val onBackgroundMuted: Color,
    val onBackgroundSoft: Color,
    val primary: Color,
    val onPrimary: Color,
    val want: Color,
    val wantStamp: Color,
    val nope: Color,
    val veryWant: Color,
    val watched: Color,
    val surfaceOverlay: Color,
    val navBar: Color,
    val scrimEnd: Color,
    /** Цвета шкалы оценки: ужасно, плохо, нормально, хорошо, отлично. */
    val rating: List<Color>,
) {
    /** Подложка под текстом поверх постера. */
    val scrim: Brush get() = Brush.verticalGradient(listOf(Color.Transparent, scrimEnd))
}

val DarkCineColors = CineColors(
    background = Color(0xFF0A0A12),
    onBackground = Color(0xFFF3F3FA),
    onBackgroundMuted = Color(0xFFB4B4CC),
    onBackgroundSoft = Color(0xFFD6D6EE),
    primary = Color(0xFFE8A2FF),
    onPrimary = Color(0xFF0A0A12),
    want = Color(0xFF2FBF7A),
    wantStamp = Color(0xFF7DE3A6),
    nope = Color(0xFFFF8A8A),
    veryWant = Color(0xFFF3D27A),
    watched = Color(0xFF7FB0E0),
    surfaceOverlay = Color(0x1AFFFFFF), // белый, 10%
    navBar = Color(0xD90A0A12),         // фон, 85%
    scrimEnd = Color(0xE60A0A14),       // 90%
    rating = listOf(
        Color(0xFFFF6B6B), Color(0xFFFFA45C), Color(0xFFC9C9D8),
        Color(0xFF8BD97F), Color(0xFF2FBF7A),
    ),
)

val LocalCineColors = staticCompositionLocalOf { DarkCineColors }