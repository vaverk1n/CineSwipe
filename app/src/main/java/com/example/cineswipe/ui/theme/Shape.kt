package com.example.cineswipe.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CineShapes(
    val card: RoundedCornerShape = RoundedCornerShape(40.dp),
    val tile: RoundedCornerShape = RoundedCornerShape(12.dp),
    val capsule: RoundedCornerShape = RoundedCornerShape(percent = 50),
)

val LocalCineShapes = staticCompositionLocalOf { CineShapes() }

fun CineShapes.toMaterial() = Shapes(small = tile, medium = tile, large = card)