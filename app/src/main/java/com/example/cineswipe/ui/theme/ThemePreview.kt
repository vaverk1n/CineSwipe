package com.example.cineswipe.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun Swatch(name: String, color: Color) {
    Row(Modifier.padding(vertical = 2.dp)) {
        Box(Modifier.size(24.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(AppTheme.spacing.m))
        Text(name, style = AppTheme.typography.label, color = AppTheme.colors.onBackground)
    }
}

@Preview(name = "Палитра", showBackground = true, backgroundColor = 0xFF0A0A12, heightDp = 640)
@Composable
private fun PalettePreview() = AppTheme {
    val c = AppTheme.colors
    Column(Modifier.padding(AppTheme.spacing.xl)) {
        listOf(
            "primary" to c.primary, "want" to c.want, "wantStamp" to c.wantStamp,
            "nope" to c.nope, "veryWant" to c.veryWant, "watched" to c.watched,
            "onBackgroundMuted" to c.onBackgroundMuted, "onBackgroundSoft" to c.onBackgroundSoft,
        ).forEach { (name, color) -> Swatch(name, color) }
        Spacer(Modifier.height(AppTheme.spacing.l))
        Row { c.rating.forEach { Box(Modifier.size(32.dp).background(it)) } }
        Spacer(Modifier.height(AppTheme.spacing.l))
        Box(Modifier.fillMaxWidth().height(48.dp).background(c.scrim))
    }
}

@Preview(name = "Типографика", showBackground = true, backgroundColor = 0xFF0A0A12)
@Composable
private fun TypographyPreview() = AppTheme {
    val t = AppTheme.typography
    val color = AppTheme.colors.onBackground
    Column(Modifier.padding(AppTheme.spacing.xl)) {
        Text("Интерстеллар", style = t.headline, color = color)
        Text("Заголовок экрана", style = t.title, color = color)
        Text("ХОЧУ", style = t.stamp, color = AppTheme.colors.wantStamp)
        Text("2014 · ★ 8.6 · 2 ч 49 мин", style = t.body, color = color)
        Text("8 из 20", style = t.bodyStrong, color = color)
        Text("Уже смотрели? Сдвиньте шкалу", style = t.caption, color = AppTheme.colors.onBackgroundSoft)
        Text("Нормально", style = t.label, color = AppTheme.colors.onBackgroundMuted)
    }
}