package com.example.cineswipe.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cineswipe.R
import com.example.cineswipe.ui.theme.AppTheme

@Composable
fun PlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize().padding(AppTheme.spacing.screenHorizontal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.m, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, style = AppTheme.typography.title, color = AppTheme.colors.onBackground)
        Text(
            stringResource(R.string.placeholder_in_progress),
            style = AppTheme.typography.body,
            color = AppTheme.colors.onBackgroundMuted,
        )
        content()
    }
}

@Composable
fun DetailsPlaceholder(movieId: Int, onBack: () -> Unit, modifier: Modifier = Modifier) {
    PlaceholderScreen(title = "Фильм #$movieId", modifier = modifier) {
        Button(onClick = onBack) { Text(stringResource(R.string.action_back)) }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A12)
@Composable
private fun PlaceholderPreview() = AppTheme { PlaceholderScreen("Подборки") }