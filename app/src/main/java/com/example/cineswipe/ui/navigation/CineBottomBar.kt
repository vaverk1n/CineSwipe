package com.example.cineswipe.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cineswipe.ui.theme.AppTheme

@Composable
fun CineBottomBar(
    selected: BottomTab?,
    onTabClick: (BottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = colors.navBar,
        contentColor = colors.onBackground,
    ) {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selected,
                onClick = { onTabClick(tab) },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(stringResource(tab.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.onBackground,
                    unselectedIconColor = colors.onBackgroundMuted,
                    unselectedTextColor = colors.onBackgroundMuted,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A12)
@Composable
private fun BottomBarSwipePreview() = AppTheme {
    CineBottomBar(selected = BottomTab.Swipe, onTabClick = {})
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A12)
@Composable
private fun BottomBarListsPreview() = AppTheme {
    CineBottomBar(selected = BottomTab.Lists, onTabClick = {})
}