package com.example.cineswipe.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cineswipe.R

val Manrope = FontFamily(
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold),
    Font(R.font.manrope_extrabold, FontWeight.ExtraBold),
)

@Immutable
data class CineTypography(
    val headline: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp, lineHeight = 32.sp, letterSpacing = (-1).sp,
    ),
    val title: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp, lineHeight = 26.sp, letterSpacing = (-0.5).sp,
    ),
    val stamp: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp, lineHeight = 34.sp,
    ),
    val body: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp, lineHeight = 20.sp,
    ),
    val bodyStrong: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.Bold,
        fontSize = 14.sp, lineHeight = 20.sp,
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp, lineHeight = 18.sp,
    ),
    val label: TextStyle = TextStyle(
        fontFamily = Manrope, fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp, lineHeight = 16.sp,
    ),
)

val LocalCineTypography = staticCompositionLocalOf { CineTypography() }

/** Те же стили для стандартных компонентов Material, чтобы они не выбивались из дизайна. */
fun CineTypography.toMaterial() = Typography(
    headlineMedium = headline,
    titleLarge = title,
    titleMedium = bodyStrong,
    bodyLarge = body,
    bodyMedium = body,
    bodySmall = caption,
    labelLarge = bodyStrong,
    labelMedium = label,
    labelSmall = label,
)